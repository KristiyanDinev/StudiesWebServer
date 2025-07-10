package project.kristiyan.WebServer.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final String authSession = "AUTHENTICATION";

    @Value("${token}")
    private String _token;

    public boolean checkTokenValidation(String given_token) {
        return _token.equals(given_token);
    }

    public boolean isAuthenticated(HttpSession session) {
        return session != null && session.getAttribute(authSession).equals("true");
    }
}
