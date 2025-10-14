package app.ec.dto;

public class PartnerBillingBean {
	public int partnerId;
    public String partnerName;
    public String username;
    public int callCount;
    public int successCount;
    public int failedCount;
    public int processingCount;
    public double bill;
    public double billRate;

    public PartnerBillingBean(int partnerId, String partnerName, String username,
                              int callCount, int successCount, int failedCount,
                              int processingCount, double bill, double billRate) {
        this.partnerId = partnerId;
        this.partnerName = partnerName;
        this.username = username;
        this.callCount = callCount;
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.processingCount = processingCount;
        this.bill = bill;
        this.billRate = billRate;
    }

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public int getProcessingCount() {
		return processingCount;
	}

	public void setProcessingCount(int processingCount) {
		this.processingCount = processingCount;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public double getBillRate() {
		return billRate;
	}

	public void setBillRate(double billRate) {
		this.billRate = billRate;
	}
    
    
}
