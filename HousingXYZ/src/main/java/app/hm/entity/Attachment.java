package app.hm.entity;


import jakarta.persistence.*;

@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // e.g., "National ID", "Trade License"
    private String type;        // e.g., "ID_DOC", "BANK_SLIP"
    private String filePath;

    // Optional: associate with a shop bid
    @ManyToOne
    @JoinColumn(name = "shop_bid_id")
    private ShopBid shopBid;

    // Optional: associate with tender bid directly
    @ManyToOne
    @JoinColumn(name = "tender_bid_id")
    private TenderBid tenderBid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ShopBid getShopBid() {
		return shopBid;
	}

	public void setShopBid(ShopBid shopBid) {
		this.shopBid = shopBid;
	}

	public TenderBid getTenderBid() {
		return tenderBid;
	}

	public void setTenderBid(TenderBid tenderBid) {
		this.tenderBid = tenderBid;
	}

    
}

