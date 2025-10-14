package app.ec.dto;


public class EcAuthErrorResDto {
	private String status;
    private String statusCode;
    private ErrorDetail error;


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


	public ErrorDetail getError() {
		return error;
	}


	public void setError(ErrorDetail error) {
		this.error = error;
	}


	public static class ErrorDetail {
        private String field;
        private String message;
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}

    }
}
