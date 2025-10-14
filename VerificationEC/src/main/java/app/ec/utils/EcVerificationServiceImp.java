package app.ec.utils;

import java.util.HashMap;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import app.ec.dto.EcAuthResDto;
import app.ec.dto.EcLoginReqDto;
import app.ec.model.EcTokenInfo;
import app.ec.repo.EcTokenInfoRepo;




@Service
public class EcVerificationServiceImp  implements EcVerificationService{
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final Logger LOGGER = LoggerFactory.getLogger(EcVerificationServiceImp.class);
	@Value("${ecommsion.base.url}")
	private String eCommisionUrl;
	@Value("${ecommsion.user}")
	private String eCommisionUser;
	@Value("${ecommsion.password}")
	private String eCommisionPassword;


	@Autowired
	PojoUtils pojoUtils;
	@Autowired EcTokenInfoRepo ecTokenInfoRepo;

		
	public String Login() {
		String token=null;;
		try {
			EcLoginReqDto ecLoginReqDto = new EcLoginReqDto(eCommisionUser, eCommisionPassword);
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, pojoUtils.ClassToJson(ecLoginReqDto));
			Request request = new Request.Builder().url(eCommisionUrl + "/rest/auth/login").post(body).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				EcAuthResDto authResDto = pojoUtils.JsonToClass(response.body().string(), EcAuthResDto.class);
				EcTokenInfo ecTokenInfo = new EcTokenInfo(authResDto.getSuccess().getData().get("username").toString(),
						authResDto.getSuccess().getData().get("access_token").toString(),
						authResDto.getSuccess().getData().get("refresh_token").toString());
				ecTokenInfo.setGeneratedTime(System.currentTimeMillis());
				ecTokenInfo.setExpDuration(12*60*60-120);
				ecTokenInfoRepo.save(ecTokenInfo);	
				ecTokenInfoRepo.flush();
				LOGGER.info(pojoUtils.ClassToJson(ecTokenInfo));
				token= ecTokenInfo.getAccessToken();
			} else {
				LOGGER.info(response.body().string());
				return null;
			}
		} catch (Exception e) {
			LOGGER.info("Network Error");
		}
		return token;
	}

	public boolean LogOut() {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(eCommisionUrl + "/rest/auth/logout").get()
					.addHeader("Authorization", "Bearer " + GetToken()).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				if(ecTokenInfoRepo.existsById(eCommisionUser)) {
					ecTokenInfoRepo.deleteById(eCommisionUser);
				}
				LOGGER.info(response.body().string());
				return true;
			} else {
				LOGGER.info(response.body().string());
				return false;
			}

		} catch (Exception e) {
			LOGGER.info("Network Error");
			return false;
		}
	}

	
	public Map<String, String> GetPartnerBillInfo(String requestBody ) {
		
		
		

		Map<String, String> verifyMap = new HashMap<String, String>();
		try {
			String Token=GetToken();
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, requestBody);
			Request request = new Request.Builder().url(eCommisionUrl + "/rest/partner-billing/get-billing-report").post(body)
					.addHeader("Authorization", "Bearer "+Token).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				verifyMap.put("CODE", "OK");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			} else {
				
				verifyMap.put("CODE", "ERROR");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			}
			

		} catch (Exception e) {
			verifyMap.put("CODE", "NTERROR");
			verifyMap.put("Data", "Network Error in verifyInfo");
			LOGGER.info("Network Error in verifyInfo");
			return verifyMap;
		}

	
		
		
		
		
	}
	

	String GetToken() {
		if(ecTokenInfoRepo.existsById(eCommisionUser)) {
			EcTokenInfo ecTokenInfo=ecTokenInfoRepo.findById(eCommisionUser).get();
			Long currentTime = System.currentTimeMillis();
			
			if((currentTime - ecTokenInfo.getGeneratedTime()) > ecTokenInfo.getExpDuration() * 1000){
				ecTokenInfoRepo.deleteById(eCommisionUser);
				return Login();
			}
			else {
				return ecTokenInfo.getAccessToken();
			}
		}else {
			return Login();
		}
	}

	public Map<String, String> GetEcService(String requestBody, String RestPath) {
		Map<String, String> verifyMap = new HashMap<String, String>();
		try {
			String Token=GetToken();
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, requestBody);
			Request request = new Request.Builder().url(eCommisionUrl + RestPath).post(body)
					.addHeader("Authorization", "Bearer "+Token).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				verifyMap.put("CODE", "OK");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			} else {
				
				verifyMap.put("CODE", "ERROR");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			}
			

		} catch (Exception e) {
			verifyMap.put("CODE", "NTERROR");
			verifyMap.put("Data", "Network Error in verifyInfo");
			LOGGER.info("Network Error in verifyInfo");
			return verifyMap;
		}

	}


	
	
	@Override
	public String AuthUser(String username, String password) {
		String token=null;;
		try {
			EcLoginReqDto ecLoginReqDto = new EcLoginReqDto(username, password);
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, pojoUtils.ClassToJson(ecLoginReqDto));
			Request request = new Request.Builder().url(eCommisionUrl + "/rest/auth/login").post(body).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				EcAuthResDto authResDto = pojoUtils.JsonToClass(response.body().string(), EcAuthResDto.class);
				EcTokenInfo ecTokenInfo = new EcTokenInfo(authResDto.getSuccess().getData().get("username").toString(),
						authResDto.getSuccess().getData().get("access_token").toString(),
						authResDto.getSuccess().getData().get("refresh_token").toString());
				ecTokenInfo.setGeneratedTime(System.currentTimeMillis());
				ecTokenInfo.setExpDuration(12*60*60-120);
				ecTokenInfoRepo.save(ecTokenInfo);	
				ecTokenInfoRepo.flush();
				LOGGER.info(pojoUtils.ClassToJson(ecTokenInfo));
				token= ecTokenInfo.getAccessToken();
			}
			else {
				return "Fail";
			}
		} catch (Exception e) {
			return "Fail";
		}
		return token;
	}
	
	
	
	
	String getToken(String eCommissionUser) {
	    return ecTokenInfoRepo.findById(eCommissionUser)
	        .filter(info -> (System.currentTimeMillis() - info.getGeneratedTime()) <= info.getExpDuration() * 1000)
	        .map(EcTokenInfo::getAccessToken)
	        .orElseGet(() -> {
	            ecTokenInfoRepo.deleteById(eCommissionUser);
	            return null;
	        });
	}

	
	
	
	public Map<String, String> GetEcService(String requestBody, String RestPath,String eCommisionUser) {
		Map<String, String> verifyMap = new HashMap<String, String>();
		try {
			String Token=getToken(eCommisionUser);
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, requestBody);
			Request request = new Request.Builder().url(eCommisionUrl + RestPath).post(body)
					.addHeader("Authorization", "Bearer "+Token).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				verifyMap.put("CODE", "OK");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			} else {
				
				verifyMap.put("CODE", "ERROR");
				verifyMap.put("Data", response.body().string());
				return verifyMap;
			}
			

		} catch (Exception e) {
			verifyMap.put("CODE", "NTERROR");
			verifyMap.put("Data", "Network Error in verifyInfo");
			LOGGER.info("Network Error in verifyInfo");
			return verifyMap;
		}

	}

	
	
	
	public boolean LogOut(String eCommisionUser) {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(eCommisionUrl + "/rest/auth/logout").get()
					.addHeader("Authorization", "Bearer " + getToken(eCommisionUser)).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				if(ecTokenInfoRepo.existsById(eCommisionUser)) {
					ecTokenInfoRepo.deleteById(eCommisionUser);
				}
				LOGGER.info(response.body().string());
				return true;
			} else {
				LOGGER.info(response.body().string());
				return false;
			}

		} catch (Exception e) {
			LOGGER.info("Network Error");
			return false;
		}
	}
	
	
	
	
	
	
}
