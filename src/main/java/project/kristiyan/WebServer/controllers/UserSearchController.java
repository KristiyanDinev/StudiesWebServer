package project.kristiyan.WebServer.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.services.SearchService;
import project.kristiyan.WebServer.services.SermonService;
import project.kristiyan.WebServer.services.SongService;
import project.kristiyan.WebServer.services.StudyService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserSearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SermonService sermonService;

    @Autowired
    private SongService songService;

    @Autowired
    private StudyService studyService;

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

    @GetMapping("/song/categories")
    public ResponseEntity<List<String>> getAllSongCategories() {
        return ResponseEntity.ok(WebServerApplication.database.songCategoryDao.getCategories_SearchSelect());
    }

    @GetMapping("/song/playlists")
    public ResponseEntity<List<String>> getAllSongPlaylists() {
        return ResponseEntity.ok(WebServerApplication.database.songPlaylistDao.getPlaylists_SearchSelect());
    }

    @GetMapping("/sermon/categories")
    public ResponseEntity<List<String>> getAllSermonCategories() {
        return ResponseEntity.ok(WebServerApplication.database.sermonCategoryDao.getCategories_SearchSelect());
    }

    @GetMapping("/sermon/playlists")
    public ResponseEntity<List<String>> getAllSermonPlaylists() {
        return ResponseEntity.ok(WebServerApplication.database.sermonPlaylistDao.getPlaylists_SearchSelect());
    }

    @PostMapping("/search/studies")
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

    @PostMapping("/search/songs")
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

    @PostMapping("/search/sermons")
    public ResponseEntity<HttpStatus> searchSermons(@RequestParam()
                                                    String alike_sermon,
                                                    @RequestParam(required = false)
                                                    List<String> categories,
                                                    @RequestParam(required = false)
                                                    List<String> playlists,
                                                    HttpSession session) {
        if (alike_sermon.isBlank() &&
                (categories == null || categories.isEmpty()) &&
                (playlists == null || playlists.isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        categories = categories == null ? new ArrayList<>() : categories;
        playlists = playlists == null ? new ArrayList<>() : playlists;
        searchService.setSermonSearchQuery(alike_sermon, categories, playlists, session);
        sermonService.setSearchEngineResults(alike_sermon, categories, playlists, 1, session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
