package project.kristiyan.WebServer.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.services.SearchService;

import java.util.List;

@Controller
public class UserSearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search/study/clear")
    public ResponseEntity<HttpStatus> clearStudySearch(HttpSession session) {
        searchService.deleteStudySearchQuery(session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search/song/clear")
    public ResponseEntity<HttpStatus> clearSongSearch(HttpSession session) {
        searchService.deleteSongSearchQuery(session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search/sermon/clear")
    public ResponseEntity<HttpStatus> clearSermonSearch(HttpSession session) {
        searchService.deleteSermonSearchQuery(session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/studies/series")
    public ResponseEntity<List<String>> getAllSeries() {
        return ResponseEntity.ok(WebServerApplication.database.studySeriesDao.getSeries());
    }
}
