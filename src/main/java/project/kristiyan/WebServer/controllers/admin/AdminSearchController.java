package project.kristiyan.WebServer.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.StudySeriesEntity;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminSearchController {


    @PostMapping("/admin/search/studies")
    public ResponseEntity<List<StudySeriesEntity>> searchStudies(@RequestParam()
                                                                 String alike_study,
                                                                 @RequestParam()
                                                                 List<String> series) {
        return ResponseEntity.ok(WebServerApplication.database
                .studySeriesDao.getSearchEngineResults(alike_study, series, 1));
    }
}
