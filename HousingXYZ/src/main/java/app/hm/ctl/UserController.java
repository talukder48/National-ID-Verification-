package app.hm.ctl;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.hm.config.LayoutUtils;
import app.hm.entity.LoanApplication;
import app.hm.entity.LoanDocument;
import app.hm.entity.TenderBid;
import app.hm.entity.User;
import app.hm.enums.LoanStatus;
import app.hm.repo.LoanApplicationRepository;
import app.hm.repo.LoanDocumentRepository;
import app.hm.repo.ShopRepository;
import app.hm.repo.TenderBidRepository;
import app.hm.repo.UserRepository;
import app.hm.service.UserService;

@Controller
public class UserController {
	
	
	
	   @Autowired
	    private LayoutUtils layoutUtils;

	    @Autowired
	    private ShopRepository shopRepository;

	    @Autowired
	    private TenderBidRepository tenderBidRepository;
	    @Autowired UserRepository userRepository;
    
	@Autowired UserService userService;
    @Autowired LoanDocumentRepository loanDocumentRepository;
    
    
    @Autowired LoanApplicationRepository loanApplicationRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        // Redirect based on role
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/approver/dashboard";
        }
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_APPROVER"))) {
            return "redirect:/approver/dashboard";
        }
        // Default to bidder
        return "redirect:/bidder/dashboard";
    }

    @GetMapping("/bidder/dashboard")
    public String bidderDashboard(Model model, Authentication auth) {
    	User user=userRepository.findByUsername(auth.getName()).get();
        List<TenderBid> myBids = tenderBidRepository.findByUser(user);

        model.addAttribute("totalBids", myBids.size());
        model.addAttribute("approvedBids", myBids.stream().filter(b -> "APPROVED".equals(b.getStatus())).count());
        model.addAttribute("rejectedBids", myBids.stream().filter(b -> "REJECTED".equals(b.getStatus())).count());
        model.addAttribute("myBids", myBids.stream().sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate())).toList());

        model.addAttribute("content", "tender/dashboard"); // Thymeleaf content file
        return layoutUtils.getLayout(auth);
    }

    @GetMapping("/approver/dashboard")
    public String approverDashboard(Model model, Authentication auth) {
        List<TenderBid> allBids = tenderBidRepository.findAll();

        model.addAttribute("totalBids", allBids.size());
        model.addAttribute("pendingBids", allBids.stream().filter(b -> "SUBMITTED".equals(b.getStatus())).count());
        model.addAttribute("approvedBids", allBids.stream().filter(b -> "APPROVED".equals(b.getStatus())).count());
        model.addAttribute("recentBids", allBids.stream()
                .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                .limit(5)
                .toList());

        model.addAttribute("content", "approver/dashboard"); // Thymeleaf content file
        return layoutUtils.getLayout(auth);
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model,Authentication auth) {
        // For admin, you can reuse bidder content or create admin-specific dashboard
        model.addAttribute("content", "admin/dashboard");
        return layoutUtils.getLayout(auth);
    }

}
