package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaylistSermonController {

    @GetMapping("/playlists/sermon/add")
    public String addSermonToPlaylistPage() {
        return "playlist/sermon/playlist_sermon_add";
    }

    @GetMapping("/playlists/sermon/remove")
    public String removeSermonFromPlaylistPage() {
        return "playlist/sermon/playlist_sermon_remove";
    }

    @GetMapping("/playlists/sermon/delete")
    public String deleteSermonPlaylistPage() {
        return "playlist/sermon/playlist_sermon_delete";
    }
}
