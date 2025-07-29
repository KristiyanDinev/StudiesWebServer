package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.database.entities.SongEntity;

import java.util.List;


@Controller
public class PlaylistSongController {

    @GetMapping("/playlists/song/add")
    public String addSongToPlaylistPage() {
        return "playlist/song/playlist_song_add";
    }

    @GetMapping("/playlists/song/remove")
    public String removeSongFromPlaylistPage() {
        return "playlist/song/playlist_song_remove";
    }

    @GetMapping("/playlists/song/delete")
    public String deleteSongPlaylistPage() {
        return "playlist/song/playlist_song_delete";
    }



    @PostMapping("/playlists/song/alike")
    public ResponseEntity<List<SongEntity>> getAlikeSongs(@RequestBody String alike_song) {
        return ResponseEntity.ok(WebServerApplication.database.songDao.getAllSongsAlike(alike_song));
    }
}
