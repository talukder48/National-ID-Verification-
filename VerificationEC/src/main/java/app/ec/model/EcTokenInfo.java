package app.ec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "nbfi_ec_token_info")
public class EcTokenInfo {
	@Id
	@Column (name = "username",nullable = false)
	private String username;
	@Lob
	@Column (name = "accessToken",nullable = false)
	private String accessToken;
	@Lob
	@Column (name = "refreshToken",nullable = false)
	private String refreshToken;
	@Column (name = "generatedTime")
	private Long generatedTime;
	@Column (name = "expDuration",length=4)
	private int expDuration;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}



	public Long getGeneratedTime() {
		return generatedTime;
	}

	public void setGeneratedTime(Long generatedTime) {
		this.generatedTime = generatedTime;
	}

	public int getExpDuration() {
		return expDuration;
	}

	public void setExpDuration(int expDuration) {
		this.expDuration = expDuration;
	}

	public EcTokenInfo(String username, String accessToken, String refreshToken) {
		super();
		this.username = username;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public EcTokenInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

}
