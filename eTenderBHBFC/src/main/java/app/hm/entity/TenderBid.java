package app.hm.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
public class TenderBid {
    @Id @GeneratedValue
    private Long id;
    private LocalDate createdDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tender_bid_id")
    private List<ShopBid> shopBids;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tender_bid_id")
    private List<Attachment> attachments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	private Double totalArea;

	@Transient
	private Double totalSecurity;
	@Transient private Double totalAnnualRent;

	
	public Double getTotalAnnualRent() {
		return totalAnnualRent;
	}

	public void setTotalAnnualRent(Double totalAnnualRent) {
		this.totalAnnualRent = totalAnnualRent;
	}

	public Double getTotalArea() { return totalArea; }
	public void setTotalArea(Double totalArea) { this.totalArea = totalArea; }

	public Double getTotalSecurity() { return totalSecurity; }
	public void setTotalSecurity(Double totalSecurity) { this.totalSecurity = totalSecurity; }


	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ShopBid> getShopBids() {
		return shopBids;
	}

	public void setShopBids(List<ShopBid> shopBids) {
		this.shopBids = shopBids;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

    
}
