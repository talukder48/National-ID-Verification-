package app.ec.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "nbfi_ec_log")
public class ECommissionLog {
	@Id
	@Column (name = "logId",nullable = false)
	private String logId;
	@Column (name = "urlPath",length=200)
	private String urlPath;
	@Column (name = "requestBody",length=250)
	private String requestBody;
	@Lob
	@Column (name = "responseBody")
	private String responseBody;
	@Column (name = "processTime")
	private Date processTime;
	@Column (name = "verifyBy",length=50)
	private String verifyBy;
	public ECommissionLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ECommissionLog(String logId, String urlPath, String requestBody, String responseBody, Date processTime,
			String verifyBy) {
		super();
		this.logId = logId;
		this.urlPath = urlPath;
		this.requestBody = requestBody;
		this.responseBody = responseBody;
		this.processTime = processTime;
		this.verifyBy = verifyBy;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
	public String getVerifyBy() {
		return verifyBy;
	}
	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}
	
	
}
