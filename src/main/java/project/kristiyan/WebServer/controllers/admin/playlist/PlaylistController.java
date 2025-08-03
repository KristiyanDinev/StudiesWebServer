package project.kristiyan.WebServer.controllers.admin.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaylistController {

    @GetMapping("/admin/playlists")
    public String playlistIndex() {
        return "admin/playlist/playlist_index";
    }
}
