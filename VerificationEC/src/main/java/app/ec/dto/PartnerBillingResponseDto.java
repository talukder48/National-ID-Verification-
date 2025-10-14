package app.ec.dto;

import java.util.List;

public class PartnerBillingResponseDto {
	 public String generatedTime;
     public int partnerId;
     public int totalCallCount;
     public int totalSuccessCount;
     public int totalFailedCount;
     public int totalProcessingCount;
     public double totalBill;
     public List<PartnerBillingBean> partnerBillingBeanList;
	public String getGeneratedTime() {
		return generatedTime;
	}
	public void setGeneratedTime(String generatedTime) {
		this.generatedTime = generatedTime;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getTotalCallCount() {
		return totalCallCount;
	}
	public void setTotalCallCount(int totalCallCount) {
		this.totalCallCount = totalCallCount;
	}
	public int getTotalSuccessCount() {
		return totalSuccessCount;
	}
	public void setTotalSuccessCount(int totalSuccessCount) {
		this.totalSuccessCount = totalSuccessCount;
	}
	public int getTotalFailedCount() {
		return totalFailedCount;
	}
	public void setTotalFailedCount(int totalFailedCount) {
		this.totalFailedCount = totalFailedCount;
	}
	public int getTotalProcessingCount() {
		return totalProcessingCount;
	}
	public void setTotalProcessingCount(int totalProcessingCount) {
		this.totalProcessingCount = totalProcessingCount;
	}
	public double getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	public List<PartnerBillingBean> getPartnerBillingBeanList() {
		return partnerBillingBeanList;
	}
	public void setPartnerBillingBeanList(List<PartnerBillingBean> partnerBillingBeanList) {
		this.partnerBillingBeanList = partnerBillingBeanList;
	}
     
}
