package project.kristiyan.WebServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyController {

    @GetMapping("/studies")
    public String studies() {
        return "study/studies";
    }
}
