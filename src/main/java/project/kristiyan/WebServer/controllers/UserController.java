package project.kristiyan.WebServer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        if (userService.isAuthenticated(request.getSession(false))) {
            return "redirect:/studies";
        }
        return "index";
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> login(@RequestParam String token,
                                            HttpServletRequest request) {
        if (userService.checkTokenValidation(token)) {
            HttpSession session = request.getSession(true);
            session.setAttribute(UserService.authSession, "true");
            return ResponseEntity.status(HttpStatus.OK).build();

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
