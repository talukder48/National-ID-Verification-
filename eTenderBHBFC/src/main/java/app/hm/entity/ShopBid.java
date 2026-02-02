package app.hm.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ShopBid {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Shop shop;

    private Double rentAmount;
    private Double securityAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_bid_id")
    private List<Attachment> attachments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Double getRentAmount() {
		return rentAmount;
	}

	public void setRentAmount(Double rentAmount) {
		this.rentAmount = rentAmount;
	}

	public Double getSecurityAmount() {
		return securityAmount;
	}

	public void setSecurityAmount(Double securityAmount) {
		this.securityAmount = securityAmount;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
    
    
}

