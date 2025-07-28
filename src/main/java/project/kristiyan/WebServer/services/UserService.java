package project.kristiyan.WebServer.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final String authSession = "AUTHENTICATION";

    private String _token;

    public UserService() {
        _token = System.getenv("WEBAUTH_TOKEN");
    }


    public boolean checkTokenValidation(String given_token) {
        return _token.equals(given_token);
    }

    public boolean isAuthenticated(HttpSession session) {
        return session != null && "true".equals(session.getAttribute(authSession));
    }
}
