package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.SermonEntity;
import project.kristiyan.database.entities.SongEntity;

import java.util.List;

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


    @PostMapping("/playlists/sermon/alike")
    public ResponseEntity<List<SermonEntity>> getAlikeSermons(@RequestParam String alike_sermon) {
        return ResponseEntity.ok(WebServerApplication.database.sermonDao.getAllSermonsAlike(alike_sermon));
    }

    @PostMapping("/playlists/sermon/add")
    public ResponseEntity<HttpStatus> addSermonToPlaylistDB(@RequestParam String sermon,
                                                          @RequestParam String playlist) {
        SermonEntity sermonEntity = WebServerApplication.database.sermonDao.getSermon(sermon);
        if (sermonEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.sermonPlaylistDao.addSermonToPlaylist(
                sermonEntity.id, playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/playlists/sermon/remove")
    public ResponseEntity<HttpStatus> removeSermonFromPlaylistDB(@RequestParam String sermon,
                                                               @RequestParam String playlist) {
        SermonEntity sermonEntity = WebServerApplication.database.sermonDao.getSermon(sermon);
        if (sermonEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.sermonPlaylistDao.deleteSermonFromPlaylist(
                sermonEntity.id, playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/playlists/sermon/delete")
    public ResponseEntity<HttpStatus> deletePlaylistDB(@RequestParam String playlist) {
        if (WebServerApplication.database.sermonPlaylistDao.deletePlaylist(playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
