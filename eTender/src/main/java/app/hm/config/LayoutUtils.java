package app.hm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class LayoutUtils {

	public String getLayout(Authentication authentication) {
		
		 if (authentication.getAuthorities().stream()
                 .anyMatch(a -> a.getAuthority().equals("ROLE_BIDDER"))) {
             return "layout/bidder-layout";
         }
		 else
			 if (authentication.getAuthorities().stream()
	                 .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	             return "layout/admin-layout";
	         }
			 else {
				 return "layout/super-layout";
			 }
			 
		
	}
	
	/*
	 * public String gethome(Authentication authentication) { User user =
	 * userService.getUserByEmail(authentication.getName()); String LayoutName;
	 * switch (user.getUserRole().toString()) { case "ADMIN": { LayoutName =
	 * "admin-home"; break; } case "USER": { LayoutName = "user-home"; break; } case
	 * "SUPER": { LayoutName = "home"; break; } default: LayoutName = "home"; break;
	 * } return LayoutName; }
	 */
}
