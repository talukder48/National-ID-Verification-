package app.ec.ctl;

import java.util.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import app.ec.dto.*;
import app.ec.model.ECommissionLog;
import app.ec.repo.ECommissionLogRepo;
import app.ec.utils.EcVerificationService;
import app.ec.utils.PojoUtils;
import app.ec.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EcVerificationCtl {

	@Autowired
	private EcVerificationService ecVerificationService;

	@Autowired
	private PojoUtils pojoUtils;

	@Autowired
	private ECommissionLogRepo eCommissionLogRepo;

	/* ---------- HOME ---------- */
	@GetMapping("/")
	public String home(Model model,HttpServletRequest request) {
		SessionUtils sessionUtils = new SessionUtils(request.getSession(false));
		if (sessionUtils.isInvalidSession())
			return "index";
		else {
			return "redirect:/NidVerification.do";
		}
	}

	/* ---------- LOGIN ---------- */
	@PostMapping("/loginVerify")
	public String verifyLogin(@RequestParam String username, @RequestParam String password, Model model,
			HttpServletRequest request) {

		String token = ecVerificationService.AuthUser(username, password);

		if ("fail".equalsIgnoreCase(token)) {
			model.addAttribute("error", true);
			return "index";
		}

		HttpSession session = Optional.ofNullable(request.getSession(false)).orElse(request.getSession());
		session.setAttribute("UserID", username);
		session.setAttribute("token", token);

		return "redirect:/NidVerification.do";
	}

	/* ---------- LOGOUT ---------- */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SessionUtils sessionUtils = new SessionUtils(request.getSession(false));		
//		ecVerificationService.LogOut(sessionUtils.getSessionuserId());
		if (sessionUtils.isInvalidSession())return "redirect:/";
		sessionUtils.invalidate(); // clear session
		return "redirect:/";
	}

	/* ---------- NID VERIFICATION PAGE ---------- */
	@GetMapping("/NidVerification.do")
	public String NidVerification(HttpServletRequest request, Model model) {
		SessionUtils sessionUtils = new SessionUtils(request.getSession(false));
		if (sessionUtils.isInvalidSession())
			return "redirect:/";

		model.addAttribute("nidDTO", new VerifyNidReq());
		return "EcVerification.html";
	}

	/* ---------- BORROWER VERIFICATION ---------- */
	@PostMapping("/BorrowerVerification")
	@ResponseBody
	public String borrowerVerification(@RequestBody VerifyNidReq verifyNidReq, HttpServletRequest request) {

		SessionUtils sessionUtils = new SessionUtils(request.getSession(false));
		if (sessionUtils.isInvalidSession())
			return "redirect:/";

		JSONObject response = new JSONObject();

		Map<String, String> identify = Map.of(verifyNidReq.getType(), verifyNidReq.getNid());
		Map<String, String> verify = Map.of("name", verifyNidReq.getName(), "nameEn", verifyNidReq.getNameEn(),
				"dateOfBirth", verifyNidReq.getDob().toString(), "father", verifyNidReq.getFname(), "mother",
				verifyNidReq.getMname());

		EcVoterInfoReqDto reqDto = new EcVoterInfoReqDto(identify, verify);
		Map<String, String> result = ecVerificationService.GetEcService(pojoUtils.ClassToJson(reqDto),
				"/rest/voter/demographic/verification", sessionUtils.getSessionuserId());

		handleVerificationResponse(result, verifyNidReq, response);

		response.put("UserInfo", sessionUtils.getSessionuserId());

		// Save Log
		saveECommissionLog(verifyNidReq, result, sessionUtils);

		return response.toString();
	}

	/* ---------- BILL INFO ---------- */
	@GetMapping("/GetBillInfo.do")
	public String getPartnerBillInfo(@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate, HttpServletRequest request, Model model) {

		SessionUtils sessionUtils = new SessionUtils(request.getSession(false));
		if (sessionUtils.isInvalidSession())
			return "redirect:/";
		PartnerBillingResponseDto dto = null;
		List<PartnerBillingBean> userWiseList = null;
		if (fromDate != null && toDate != null) {
			BillingParamDto billingParamDto = new BillingParamDto(fromDate, toDate);

			Map<String, String> result = ecVerificationService
					.GetPartnerBillInfo(pojoUtils.ClassToJson(billingParamDto));

			if ("OK".equalsIgnoreCase(result.get("CODE"))) {
				PartnerBillingResponse partnerResponse = pojoUtils.JsonToClass(result.get("Data"),
						PartnerBillingResponse.class);
				BillInfoSuccess billInfoSuccess = partnerResponse.getSuccess();
				dto = billInfoSuccess.getData();
				userWiseList = dto.getPartnerBillingBeanList();
			} else {
				dto = new PartnerBillingResponseDto();
				userWiseList = dto.getPartnerBillingBeanList();
				model.addAttribute("errorMessage", "Error: " + result.get("Data")); // Pass error to Thymeleaf

			}

		}else {
			dto =new PartnerBillingResponseDto();
		}
		

		// Add attributes for Thymeleaf
		model.addAttribute("BillInfo", dto);
		model.addAttribute("BillInfoUserWise", userWiseList);

		// Preserve selected dates in the UI
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);

		return "billinfo.html";
	}

	/* ---------- HELPER METHODS ---------- */
	private void handleVerificationResponse(Map<String, String> result, VerifyNidReq verifyNidReq, JSONObject obj) {
		Map<String, Boolean> fieldResult = new HashMap<>();

		if ("OK".equalsIgnoreCase(result.get("CODE"))) {
			EcVoterInfoResOnSuccessDto success = pojoUtils.JsonToClass(result.get("Data"),
					EcVoterInfoResOnSuccessDto.class);
			if (success.isVerified()) {
				obj.put("status", "200");
				obj.put("statusMessage", "গ্রাহকের সকল তথ্য সঠিক আছে।");
				fieldResult = success.getFieldVerificationResult();
				obj.put("photo", success.getSuccess().getData().get("photo"));
			}
		} else if ("ERROR".equalsIgnoreCase(result.get("CODE"))) {
			EcVoterInfoResOnFailureDto failure = pojoUtils.JsonToClass(result.get("Data"),
					EcVoterInfoResOnFailureDto.class);
			if (!failure.isVerified()) {
				obj.put("status", "200");
				obj.put("statusMessage", "গ্রাহকের সকল তথ্য সঠিক নাই।");
				fieldResult = failure.getFieldVerificationResult();
			}
		} else {
			obj.put("status", "201");
			obj.put("statusMessage", "NID Verified Successfully");
		}

		obj.put("nameStatus", fieldResult.get("name"));
		obj.put("nameEnStatus", fieldResult.get("nameEn"));
		obj.put("fanmeStatus", fieldResult.get("father"));
		obj.put("mnameStatus", fieldResult.get("mother"));
		obj.put("dobStatus", fieldResult.get("dateOfBirth"));

		obj.put("name", verifyNidReq.getName());
		obj.put("nameEn", verifyNidReq.getNameEn());
		obj.put("fanme", verifyNidReq.getFname());
		obj.put("mname", verifyNidReq.getMname());
		obj.put("dob", verifyNidReq.getDob());
	}

	private void saveECommissionLog(VerifyNidReq verifyNidReq, Map<String, String> result, SessionUtils sessionUtils) {
		ECommissionLog log = new ECommissionLog();
		log.setLogId(UUID.randomUUID().toString());
		log.setRequestBody(pojoUtils.ClassToJson(verifyNidReq));
		log.setResponseBody(pojoUtils.ClassToJson(result));
		log.setUrlPath("/rest/voter/demographic/verification");
		log.setVerifyBy(sessionUtils.getSessionuserId());
		log.setProcessTime(sessionUtils.getCurrentDate());
		eCommissionLogRepo.save(log);
	}
}
