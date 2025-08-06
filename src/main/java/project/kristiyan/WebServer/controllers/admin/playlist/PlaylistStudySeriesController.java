package project.kristiyan.WebServer.controllers.admin.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.StudySeriesEntity;

import java.util.List;

@Controller
public class PlaylistStudySeriesController {

    @GetMapping("/admin/playlists/series/add")
    public String addStudyToSeriesPage() {
        return "admin/playlist/series/playlist_series_add";
    }

    @GetMapping("/admin/playlists/series/remove")
    public String removeStudyFromSeriesPage() {
        return "admin/playlist/series/playlist_series_remove";
    }

    @GetMapping("/admin/playlists/series/delete")
    public String deleteSeriesPage() {
        return "admin/playlist/series/playlist_series_delete";
    }


    @PostMapping("/admin/playlists/study/alike_by_series")
    public ResponseEntity<List<StudySeriesEntity>> getAlikeStudiesBySeries(@RequestParam
                                                                               String alike_study,
                                                                           @RequestParam
                                                                           String series) {
        return ResponseEntity.ok(WebServerApplication.database
                .studySeriesDao.getAllStudiesAlikeBySeries(alike_study, series));
    }


    @PostMapping("/admin/playlists/study/alike")
    public ResponseEntity<List<StudySeriesEntity>> getAlikeStudies(@RequestParam
                                                                       String alike_study) {
        return ResponseEntity.ok(WebServerApplication.database
                .studySeriesDao.getAllStudiesAlike(alike_study));
    }


    @PostMapping("/admin/playlists/study/add")
    public ResponseEntity<HttpStatus> addStudyToSeriesDB(@RequestParam String study,
                                                          @RequestParam String series) {
        if (WebServerApplication.database.studySeriesDao.isStudyInSeries(study, series)) {
            // the study already is in that series
            return ResponseEntity.ok().build();
        }
        if (WebServerApplication.database.studySeriesDao
                .addStudyToSeries(study, series)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/admin/playlists/study/remove")
    public ResponseEntity<HttpStatus> removeStudyFromSeriesDB(@RequestParam String study,
                                                         @RequestParam String series) {
        if (WebServerApplication.database.studySeriesDao
                .deleteStudyFromSeries(study, series)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/admin/playlists/study/delete")
    public ResponseEntity<HttpStatus> removeSeriesDB(@RequestParam String series) {
        if (WebServerApplication.database.studySeriesDao.deleteSeries(series)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
