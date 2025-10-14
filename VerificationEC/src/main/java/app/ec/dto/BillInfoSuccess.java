package app.ec.dto;

public class BillInfoSuccess {

	public PartnerBillingResponseDto data;
	public BillInfoSuccess(PartnerBillingResponseDto data) {
		this.data = data;
	}
	public PartnerBillingResponseDto getData() {
		return data;
	}
	public void setData(PartnerBillingResponseDto data) {
		this.data = data;
	}


}
