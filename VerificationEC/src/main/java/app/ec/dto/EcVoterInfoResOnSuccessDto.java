package app.ec.dto;

import java.util.Map;

public class EcVoterInfoResOnSuccessDto {
	private String status;
	private String statusCode;
	private Success success;
	private boolean verified;
	private Map<String, Boolean> fieldVerificationResult;

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

	public Success getSuccess() {
		return success;
	}

	public void setSuccess(Success success) {
		this.success = success;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Map<String, Boolean> getFieldVerificationResult() {
		return fieldVerificationResult;
	}

	public void setFieldVerificationResult(Map<String, Boolean> fieldVerificationResult) {
		this.fieldVerificationResult = fieldVerificationResult;
	}

	public static class Success {
		private Map<String, Object> data;

		public Map<String, Object> getData() {
			return data;
		}

		public void setData(Map<String, Object> data) {
			this.data = data;
		}
	}
}
