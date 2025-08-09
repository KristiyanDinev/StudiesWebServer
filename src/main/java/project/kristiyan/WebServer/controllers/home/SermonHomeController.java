package project.kristiyan.WebServer.controllers.home;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.SermonService;

@Controller
public class SermonHomeController {

    @Autowired
    private SermonService sermonService;

    @GetMapping("/home/sermons")
    public String getSermons(Model model,
                             @RequestParam(defaultValue = "1")
                             int page,
                             HttpSession session) {
        model.addAttribute("sermons", sermonService.getPage(page, session));
        return "home/sermon/sermons";
    }
}
