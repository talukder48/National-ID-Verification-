package app.hm.ctl;

import app.hm.config.LayoutUtils;
import app.hm.entity.TenderBid;
import app.hm.repo.TenderBidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/approver")
public class ApproverController {

    private final TenderBidRepository tenderBidRepository;

    public ApproverController(TenderBidRepository tenderBidRepository) {
        this.tenderBidRepository = tenderBidRepository;
    }


    @GetMapping("/bidders/report")
    public String allBiddersReport(Model model) {
        List<TenderBid> bids = tenderBidRepository.findAll();

        bids.forEach(bid -> {
            double totalArea = bid.getShopBids().stream()
                    .mapToDouble(sb -> (sb.getShop().getAreaMainFloor() != null ? sb.getShop().getAreaMainFloor() : 0)
                                    + (sb.getShop().getAreaMezzanineFloor() != null ? sb.getShop().getAreaMezzanineFloor() : 0))
                    .sum();

            double totalRent = bid.getShopBids().stream()
                    .mapToDouble(sb -> {
                        double area = (sb.getShop().getAreaMainFloor() != null ? sb.getShop().getAreaMainFloor() : 0)
                                    + (sb.getShop().getAreaMezzanineFloor() != null ? sb.getShop().getAreaMezzanineFloor() : 0);
                        return area * (sb.getRentAmount() != null ? sb.getRentAmount() : 0);
                    })
                    .sum();

            double totalSecurity = bid.getShopBids().stream()
                    .mapToDouble(sb -> sb.getSecurityAmount() != null ? sb.getSecurityAmount() : 0)
                    .sum();

            bid.setTotalArea(totalArea);
            bid.setTotalAnnualRent(totalRent);
            bid.setTotalSecurity(totalSecurity);
        });

        model.addAttribute("bids", bids);
        return "approver/all-bidders-report";
    }


    @Autowired
    private LayoutUtils layoutUtils;

    // List all bids
    @GetMapping("/bids")
    public String viewAllBids(Model model, Authentication auth) {
        List<TenderBid> bids =
                tenderBidRepository.findAll()
                        .stream()
                        .sorted((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()))
                        .toList();

        model.addAttribute("bids", bids);
        model.addAttribute("content", "approver/bid-list");

        return layoutUtils.getLayout(auth);
    }
    
    
    @GetMapping("/tender/{id}/report")
    public String tenderBidReport(
            @PathVariable("id") Long id,
            Model model,
            Authentication auth
    ) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);

        if (bid == null) {
            return "redirect:/approver/bids";
        }

        model.addAttribute("bid", bid);

        // this should be the thymeleaf file name
        // e.g. templates/reports/tender-bid-report.html
        return "approver/tender-bid-report";
    }


    // View a single tender bid
    @GetMapping("/tender/{id}")
    public String viewTenderBid(
            @PathVariable("id") Long id,
            Model model,
            Authentication auth
    ) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if (bid == null) {
            return "redirect:/approver/bids";
        }

        model.addAttribute("bid", bid);
        model.addAttribute("content", "approver/view-bid-approve");
        return layoutUtils.getLayout(auth);
    }

    // Approve a tender bid
    @PostMapping("/tender/{id}/approve")
    public String approveTenderBid(@PathVariable("id") Long id) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if (bid != null && "SUBMITTED".equals(bid.getStatus())) {
            bid.setStatus("APPROVED");
            tenderBidRepository.save(bid);
        }
        return "redirect:/approver/tender/" + id;
    }

    // Reject a tender bid
    @PostMapping("/tender/{id}/reject")
    public String rejectTenderBid(@PathVariable("id") Long id) {
        TenderBid bid = tenderBidRepository.findById(id).orElse(null);
        if (bid != null && "SUBMITTED".equals(bid.getStatus())) {
            bid.setStatus("REJECTED");
            tenderBidRepository.save(bid);
        }
        return "redirect:/approver/tender/" + id;
    }
}
