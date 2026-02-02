package app.hm.ctl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.hm.config.LayoutUtils;
import app.hm.entity.Attachment;
import app.hm.entity.Shop;
import app.hm.entity.ShopBid;
import app.hm.entity.TenderBid;
import app.hm.entity.User;
import app.hm.repo.ShopRepository;
import app.hm.repo.TenderBidRepository;
import app.hm.repo.UserRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bidder")
public class BidderTenderController {

    @Autowired
    private LayoutUtils layoutUtils;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TenderBidRepository tenderBidRepository;
    @Autowired UserRepository userRepository;
    
    
    @GetMapping("/my-bids")
    public String myBids(Model model, Authentication auth) {
    	User user=userRepository.findByUsername(auth.getName()).get();
        // For now showing all bids
        // Later you can filter by logged-in bidder (email/phone/user)
        List<TenderBid> bids = tenderBidRepository.findByUser(user);

        model.addAttribute("bids", bids);
        model.addAttribute("content", "tender/my-bids");

        return layoutUtils.getLayout(auth);
    }
    
    
    @GetMapping("/all-bids")
    public String allBidder(Model model, Authentication auth) {

        // For now showing all bids
        // Later you can filter by logged-in bidder (email/phone/user)
        List<TenderBid> bids = tenderBidRepository.findAll();

        model.addAttribute("bids", bids);
        model.addAttribute("content", "tender/all-bids");

        return layoutUtils.getLayout(auth);
    }
    
    
    @GetMapping("/tender/{id}")
    public String viewTenderBid(
            @PathVariable("id") Long id,
            Model model,
            Authentication auth
    ) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if (bid == null) {
            return "redirect:/bidder/my-bids";
        }

        model.addAttribute("bid", bid);
        model.addAttribute("content", "tender/view-bid-single");
        return layoutUtils.getLayout(auth);
    }


    
    @GetMapping("/active-tenders")
    public String activeTender(Model model, Authentication auth) {
    	 model.addAttribute("content", "tender/active-tender");
         return layoutUtils.getLayout(auth);
    }
    
    // Step 1: show form for bidder info + shop selection
    @GetMapping("/apply-tender-step1")
    public String showTenderStep1(Model model, Authentication auth) {
    	
    	User user=userRepository.findByUsername(auth.getName()).get();
        TenderBid tenderBid = new TenderBid();
        tenderBid.setUser(user);
        // Prepare 2 empty ShopBids
        List<ShopBid> shopBids = new ArrayList<>();
        for(int i = 0; i < 2; i++) shopBids.add(new ShopBid());
        tenderBid.setShopBids(shopBids);

        model.addAttribute("tenderBid", tenderBid);
        model.addAttribute("shops", shopRepository.findAll());
        model.addAttribute("content", "tender/apply-tender-step1");
        return layoutUtils.getLayout(auth);
    }

	
    
    @PostMapping("/apply-tender-step1")
    public String submitTenderStep1(
            @ModelAttribute TenderBid tenderBid,
            @RequestParam("shopNumbers") List<Integer> shopNumbers,
            @RequestParam("rentAmounts") List<Double> rentAmounts,
            @RequestParam("securityAmounts") List<Double> securityAmounts,
            Authentication auth,
            Model model
    ) {
    	User user=userRepository.findByUsername(auth.getName()).get();
        // ---------- VALIDATION ----------
        List<String> errors = new ArrayList<>();

        if (shopNumbers == null || shopNumbers.isEmpty()) {
            errors.add("At least one shop must be selected.");
        }

        if (shopNumbers != null && shopNumbers.size() > 2) {
            errors.add("You can bid for maximum 2 shops only.");
        }

        if (rentAmounts == null || rentAmounts.stream().anyMatch(r -> r == null || r <= 0)) {
            errors.add("Rent amount must be greater than zero.");
        }

        if (securityAmounts == null || securityAmounts.stream().anyMatch(s -> s == null || s <= 0)) {
            errors.add("Security amount must be greater than zero.");
        }

        if (shopNumbers != null && shopNumbers.stream().distinct().count() != shopNumbers.size()) {
            errors.add("Same shop cannot be selected twice.");
        }

        // ---------- IF ERROR → BACK TO STEP-1 ----------
        if (!errors.isEmpty()) {
            model.addAttribute("errorMessages", errors);
            model.addAttribute("tenderBid", tenderBid);
            model.addAttribute("shops", shopRepository.findAll());
            model.addAttribute("content", "tender/apply-tender-step1");
            return layoutUtils.getLayout(auth);
        }

        // ---------- SAVE ----------
        tenderBid.setCreatedDate(LocalDate.now());
        tenderBid.setStatus("STEP1_COMPLETED");

        List<ShopBid> shopBids = new ArrayList<>();
        for (int i = 0; i < shopNumbers.size(); i++) {
            Shop shop = shopRepository.findById(shopNumbers.get(i)).orElseThrow();
            ShopBid sb = new ShopBid();
            sb.setShop(shop);
            sb.setRentAmount(rentAmounts.get(i));
            sb.setSecurityAmount(securityAmounts.get(i));
            shopBids.add(sb);
        }
        tenderBid.setShopBids(shopBids);
        tenderBid.setUser(user);
        tenderBidRepository.save(tenderBid);

        return "redirect:/bidder/apply-tender-step2/" + tenderBid.getId();
    }

    
    
    
    
    // Step 2: show attachments upload
    @GetMapping("/apply-tender-step2/{id}")
    public String showTenderStep2(@PathVariable("id") Long id, Model model,Authentication auth) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if(bid == null) return "redirect:/bidder/active-tenders";

        // Prepare 7 attachment placeholders
        List<Attachment> attachments = new ArrayList<>();
        for(int i = 0; i < 7; i++) attachments.add(new Attachment());
        bid.setAttachments(attachments);

        model.addAttribute("tenderBid", bid);
        model.addAttribute("content", "tender/apply-tender-step2");
        return layoutUtils.getLayout(auth);
    }

    // Step 2 POST: upload attachments
    @PostMapping("/apply-tender-step2/{id}")
    @Transactional
    public String submitTenderStep2(
            @PathVariable("id") Long id,
            @RequestParam("attachmentFiles") List<MultipartFile> attachmentFiles,
            @RequestParam("attachmentNames") List<String> attachmentNames,
            @RequestParam("attachmentTypes") List<String> attachmentTypes,
            RedirectAttributes ra
    ) {

        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if (bid == null) {
            ra.addFlashAttribute("error", "Tender not found");
            return "redirect:/bidder/active-tenders";
        }

        try {
            // ===== Base upload directory (stable) =====
            String baseDir = "uploads/tender/" + bid.getId() + "/";
            Path uploadPath = Paths.get(baseDir);
            Files.createDirectories(uploadPath);

            List<Attachment> attachments = new ArrayList<>();

            for (int i = 0; i < attachmentFiles.size(); i++) {

                MultipartFile file = attachmentFiles.get(i);

                // Skip empty rows
                if (file == null || file.isEmpty()) continue;

                String originalName = file.getOriginalFilename();
                String safeFileName =
                        "att_" + System.currentTimeMillis() + "_" + originalName;

                Path filePath = uploadPath.resolve(safeFileName);

                // ===== Save file =====
                Files.copy(
                        file.getInputStream(),
                        filePath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                // ===== Save attachment metadata =====
                Attachment att = new Attachment();
                att.setName(attachmentNames.get(i));
                att.setType(attachmentTypes.get(i));
                att.setFilePath(baseDir + safeFileName); // relative path
                att.setTenderBid(bid);

                attachments.add(att);
            }

            bid.setAttachments(attachments);
            tenderBidRepository.save(bid);
            bid.setStatus("SUBMITTED");
            ra.addFlashAttribute("success", "Tender documents uploaded successfully");

        } catch (IOException e) {
            throw new RuntimeException("Tender attachment upload failed", e);
        }

        return "redirect:/bidder/active-tenders";
    }

}
