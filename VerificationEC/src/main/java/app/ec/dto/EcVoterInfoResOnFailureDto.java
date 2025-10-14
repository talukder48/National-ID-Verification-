package app.ec.dto;

import java.util.Map;

public class EcVoterInfoResOnFailureDto {
	private String message;
	private String status;
	private boolean verified;
	private Map<String, Boolean> fieldVerificationResult;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
