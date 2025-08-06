package project.kristiyan.WebServer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.StudySeriesEntity;

import java.util.List;

@Controller
public class UserSearchController {

    @GetMapping("/studies/series")
    public ResponseEntity<List<StudySeriesEntity>> getAllSeries() {
        return ResponseEntity.ok(WebServerApplication.database.studySeriesDao.getSeries());
    }
}
