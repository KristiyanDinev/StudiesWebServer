package project.kristiyan.WebServer.controllers.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.SearchService;
import project.kristiyan.WebServer.services.StudyService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminSearchController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private SearchService searchService;

    @PostMapping("/admin/search/studies")
    public ResponseEntity<HttpStatus> searchStudies(@RequestParam()
                                                    String alike_study,
                                                    @RequestParam(required = false)
                                                    List<String> series,
                                                    HttpSession session) {
        if (alike_study.isBlank() && (series == null || series.isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        series = series == null ? new ArrayList<>() : series;
        searchService.setStudySearchQuery(alike_study, series, session);
        studyService.setSearchEngineResults(alike_study, series, 1, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
