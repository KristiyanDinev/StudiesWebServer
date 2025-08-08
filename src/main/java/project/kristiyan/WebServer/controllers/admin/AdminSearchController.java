package project.kristiyan.WebServer.controllers.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.SearchService;
import project.kristiyan.WebServer.services.SermonService;
import project.kristiyan.WebServer.services.SongService;
import project.kristiyan.WebServer.services.StudyService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminSearchController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private SongService songService;

    @Autowired
    private SermonService sermonService;

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

    @PostMapping("/admin/search/songs")
    public ResponseEntity<HttpStatus> searchSongs(@RequestParam()
                                                    String alike_song,
                                                    @RequestParam(required = false)
                                                    List<String> categories,
                                                    @RequestParam(required = false)
                                                    List<String> playlists,
                                                    HttpSession session) {
        if (alike_song.isBlank() &&
                (categories == null || categories.isEmpty()) &&
                (playlists == null || playlists.isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        categories = categories == null ? new ArrayList<>() : categories;
        playlists = playlists == null ? new ArrayList<>() : playlists;
        searchService.setSongSearchQuery(alike_song, categories, playlists, session);
        songService.setSearchEngineResults(alike_song, categories, playlists, 1, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
