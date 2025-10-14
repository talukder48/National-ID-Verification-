package app.ec.dto;

import java.util.Map;

import app.ec.validation.ValidIdentifyFields;
import app.ec.validation.ValidVerifyFields;


public class EcVoterInfoReqDto {
	@ValidIdentifyFields
    private Map<String, String> identify;

    @ValidVerifyFields
    private Map<String, String> verify;

	public EcVoterInfoReqDto(Map<String, String> identify, Map<String, String> verify) {
		super();
		this.identify = identify;
		this.verify = verify;
	}

	public Map<String, String> getIdentify() {
		return identify;
	}

	public void setIdentify(Map<String, String> identify) {
		this.identify = identify;
	}

	public Map<String, String> getVerify() {
		return verify;
	}

	public void setVerify(Map<String, String> verify) {
		this.verify = verify;
	}
    
    
}
