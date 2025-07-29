package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaylistController {

    @GetMapping("/playlists")
    public String playlistIndex() {
        return "playlist/playlist_index";
    }
}
