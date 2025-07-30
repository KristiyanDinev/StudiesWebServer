package project.kristiyan.WebServer.controllers.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<SongEntity>> getAlikeSongs(@RequestParam String alike_song) {
        return ResponseEntity.ok(WebServerApplication.database.songDao.getAllSongsAlike(alike_song));
    }

    @PostMapping("/playlists/song")
    public ResponseEntity<List<SongEntity>> getAllPlaylistSongs(@RequestParam String playlist) {
        return ResponseEntity.ok(WebServerApplication.database.songPlaylistDao.getAllPlaylistSongs(playlist));
    }


    @PostMapping("/playlists/song/add")
    public ResponseEntity<HttpStatus> addSongToPlaylistDB(@RequestParam String song,
                                                          @RequestParam String playlist) {
        SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
        if (songEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.songPlaylistDao.addSongToPlaylist(
                songEntity.id, playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/playlists/song/remove")
    public ResponseEntity<HttpStatus> removeSongFromPlaylistDB(@RequestParam String song,
                                                          @RequestParam String playlist) {
        SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
        if (songEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.songPlaylistDao.deleteSongFromPlaylist(
                songEntity.id, playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/playlists/song/delete")
    public ResponseEntity<HttpStatus> deletePlaylistDB(@RequestParam String playlist) {
        if (WebServerApplication.database.songPlaylistDao.deletePlaylist(playlist)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
