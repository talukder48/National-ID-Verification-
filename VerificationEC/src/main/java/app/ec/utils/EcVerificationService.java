package app.ec.utils;

import java.util.Map;




public interface EcVerificationService {
	public Map<String, String> GetEcService(String requestBody, String RestPath);		
	public Map<String, String> GetPartnerBillInfo(String requestBody);
	
	
	public String AuthUser(String username, String password);	
	public Map<String, String> GetEcService(String requestBody, String RestPath,String eCommisionUser);
	public boolean LogOut(String eCommisionUser);
}
