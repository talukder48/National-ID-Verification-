package app.ec.dto;

import java.util.Map;

public class EcAuthResDto {
	private String status;
    private String statusCode;
    private SuccessDetail success;


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


	public SuccessDetail getSuccess() {
		return success;
	}


	public void setSuccess(SuccessDetail success) {
		this.success = success;
	}


	public static class SuccessDetail {
        private Map<String, Object> data;

		public Map<String, Object> getData() {
			return data;
		}

		public void setData(Map<String, Object> data) {
			this.data = data;
		}
        
    }
	
}
