package project.kristiyan.WebServer.controllers.home;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.StudyService;

@Controller
public class StudyHomeController {

    @Autowired
    private StudyService studyService;

    @GetMapping("/home/studies")
    public String getStudies(Model model,
                             @RequestParam(defaultValue = "1")
                             int page, HttpSession session) {
        model.addAttribute("studies", studyService.getPage(page, session));
        return "home/studies";
    }
}
