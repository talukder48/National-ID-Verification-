package app.ec.dto;

public class PartnerBillingResponse {
	public String status  ;
	public String statusCode ;
	public BillInfoSuccess success;

	public PartnerBillingResponse(String status, String statusCode, BillInfoSuccess success) {
		super();
		this.status = status;
		this.statusCode = statusCode;
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public BillInfoSuccess getSuccess() {
		return success;
	}

	public void setSuccess(BillInfoSuccess success) {
		this.success = success;
	}

}

