package app.ec.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class SessionUtils {

    private final HttpSession session;

    // Constructor injection
    public SessionUtils(HttpSession session) {
        this.session = session;
    }

    /** Returns the logged-in user ID, or null if session missing */
    public String getSessionuserId() {
        return session != null ? (String) session.getAttribute("UserID") : null;
    }

    public String getToken() {
        return session != null ? (String) session.getAttribute("token") : null;
    }

    

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /** Checks whether session is invalid (missing or no user logged in) */
    public boolean isInvalidSession() {
        return session == null || getSessionuserId() == null;
    }

    /** Safely invalidate session if it exists */
    public void invalidate() {
        if (session != null) {
            session.invalidate();
        }
    }

    /** Returns raw HttpSession if needed */
    public HttpSession getRawSession() {
        return session;
    }
}
