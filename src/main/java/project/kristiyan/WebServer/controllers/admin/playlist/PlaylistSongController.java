package project.kristiyan.WebServer.controllers.admin.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.utilities.GeneralUtility;
import project.kristiyan.database.entities.song.SongEntity;

import java.util.List;


@Controller
public class PlaylistSongController {

    @GetMapping("/admin/playlists/song/add")
    public String addSongToPlaylistPage() {
        return "admin/playlist/song/playlist_song_add";
    }

    @GetMapping("/admin/playlists/song/remove")
    public String removeSongFromPlaylistPage() {
        return "admin/playlist/song/playlist_song_remove";
    }

    @GetMapping("/admin/playlists/song/delete")
    public String deleteSongPlaylistPage() {
        return "admin/playlist/song/playlist_song_delete";
    }


    @PostMapping("/admin/playlists/song/alike_by_playlist")
    public ResponseEntity<List<SongEntity>> getAlikeSongsByPlaylist(@RequestParam
                                                                    String alike_song,
                                                                    @RequestParam
                                                                    String playlist) {
        return ResponseEntity.ok(WebServerApplication.database
                .songDao.getAllSongsAlikeByPlaylist(alike_song, playlist)
                .stream().map(s -> s.song).toList());
    }

    @PostMapping("/admin/playlists/song/alike")
    public ResponseEntity<List<SongEntity>> getAlikeSongs(@RequestParam
                                                          String alike_song) {
        return ResponseEntity.ok(WebServerApplication.database
                .songDao.getAllSongsAlike(alike_song));
    }

    @PostMapping("/admin/playlists/song")
    public ResponseEntity<List<SongEntity>> getAllPlaylistSongs(@RequestParam
                                                                String playlist) {
        return ResponseEntity.ok(WebServerApplication.database.songPlaylistDao.getAllPlaylistSongs(playlist));
    }


    @PostMapping("/admin/playlists/song/add")
    public ResponseEntity<HttpStatus> addSongToPlaylistDB(@RequestParam String song,
                                                          @RequestParam String playlist) {
        SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
        if (songEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.songPlaylistDao.addSongToPlaylist(
                songEntity.id, playlist)) {
            GeneralUtility.logMessage(String.format("Added Song %s to Playlist %s", song, playlist));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/admin/playlists/song/remove")
    public ResponseEntity<HttpStatus> removeSongFromPlaylistDB(@RequestParam String song,
                                                               @RequestParam String playlist) {
        SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
        if (songEntity == null) {
            return ResponseEntity.badRequest().build();
        }
        if (WebServerApplication.database.songPlaylistDao.deleteSongFromPlaylist(
                songEntity.id, playlist)) {
            GeneralUtility.logMessage(String.format("Removed Song %s from Playlist %s", song, playlist));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/admin/playlists/song/delete")
    public ResponseEntity<HttpStatus> deletePlaylistDB(@RequestParam String playlist) {
        if (WebServerApplication.database.songPlaylistDao.deletePlaylist(playlist)) {
            GeneralUtility.logMessage(String.format("Deleted Song Playlist %s", playlist));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
