package project.kristiyan.WebServer.controllers.home;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.SongService;

@Controller
public class SongHomeController {

    @Autowired
    private SongService songService;

    @GetMapping("/home/songs")
    public String getSongs(Model model,
                           @RequestParam(defaultValue = "1")
                           int page, HttpSession session) {
        model.addAttribute("songs", songService.getPage(page, session));
        return "home/song/songs";
    }
}
