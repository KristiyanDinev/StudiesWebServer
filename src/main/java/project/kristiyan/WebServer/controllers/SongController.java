package project.kristiyan.WebServer.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {


    @GetMapping("/songs")
    public String getSongs(Model model) {
        model.addAttribute("", 1);
        return "songs";
    }
}
