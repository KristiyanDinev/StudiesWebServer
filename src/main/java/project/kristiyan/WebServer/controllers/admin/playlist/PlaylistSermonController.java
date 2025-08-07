package project.kristiyan.WebServer.controllers.admin.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.SermonEntity;

import java.util.List;

@Controller
public class PlaylistSermonController {

    @GetMapping("/admin/playlists/sermon/add")
    public String addSermonToPlaylistPage() {
        return "admin/playlist/sermon/playlist_sermon_add";
    }

    @GetMapping("/admin/playlists/sermon/remove")
    public String removeSermonFromPlaylistPage() {
        return "admin/playlist/sermon/playlist_sermon_remove";
    }

    @GetMapping("/admin/playlists/sermon/delete")
    public String deleteSermonPlaylistPage() {
        return "admin/playlist/sermon/playlist_sermon_delete";
    }


    @PostMapping("/admin/playlists/sermon/alike_by_playlist")
    public ResponseEntity<List<SermonEntity>> getAlikeSermonsByPlaylist(@RequestParam
                                                                        String alike_sermon,
                                                                        @RequestParam String playlist) {
        return ResponseEntity.ok(WebServerApplication.database
                .sermonDao.getAllSermonsAlikeByPlaylist(alike_sermon, playlist));
    }

    @PostMapping("/admin/playlists/sermon/alike")
    public ResponseEntity<List<SermonEntity>> getAlikeSermons(@RequestParam
                                                              String alike_sermon) {
        return ResponseEntity.ok(WebServerApplication.database
                .sermonDao.getAllSermonsAlike(alike_sermon));
    }


    @PostMapping("/admin/playlists/sermon")
    public ResponseEntity<List<SermonEntity>> getAllSermonsByPlaylist(@RequestParam
                                                                      String playlist) {
        return ResponseEntity.ok(WebServerApplication.database.sermonPlaylistDao.getAllPlaylistSermons(playlist));
    }

    @PostMapping("/admin/playlists/sermon/add")
    public ResponseEntity<HttpStatus> addSermonToPlaylistDB(@RequestParam
                                                            String sermon,
                                                            @RequestParam
                                                            String playlist) {
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

    @PostMapping("/admin/playlists/sermon/remove")
    public ResponseEntity<HttpStatus> removeSermonFromPlaylistDB(@RequestParam
                                                                 String sermon,
                                                                 @RequestParam
                                                                 String playlist) {
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

    @PostMapping("/admin/playlists/sermon/delete")
    public ResponseEntity<HttpStatus> deletePlaylistDB(@RequestParam
                                                       String playlist) {
        if (WebServerApplication.database.sermonPlaylistDao.deletePlaylist(playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
