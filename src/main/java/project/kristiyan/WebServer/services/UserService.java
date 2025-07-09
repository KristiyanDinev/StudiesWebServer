package project.kristiyan.WebServer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${token}")
    private String _token;

    public boolean checkTokenValidation(String given_token) {
        System.out.println(_token);
        return _token.equals(given_token);
    }
}
