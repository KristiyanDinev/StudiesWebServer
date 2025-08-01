package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaylistStudySeriesController {

    @GetMapping("/playlists/series/add")
    public String addStudyToSeriesPage() {
        return "playlist/series/playlist_series_add";
    }

    @GetMapping("/playlists/series/remove")
    public String removeStudyFromSeriesPage() {
        return "playlist/series/playlist_series_remove";
    }

    @GetMapping("/playlists/series/delete")
    public String deleteSeriesPage() {
        return "playlist/series/playlist_series_delete";
    }
}
