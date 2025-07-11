package project.kristiyan.WebServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.dto.SongUploadDto;
import project.kristiyan.WebServer.services.SongService;
import project.kristiyan.WebServer.utilities.GeneralUtility;
import project.kristiyan.database.entities.SongEntity;

import java.io.File;

@Controller
public class SongController {

    @Autowired
    public SongService songService;

    @GetMapping("/songs")
    public String getSongs(Model model,
                           @RequestParam(defaultValue = "1")
                           int page) {
        model.addAttribute("songs", songService.getPage(page));
        return "song/songs";
    }

    @GetMapping("/songs/upload")
    public String songsUpload() {
        return "song/song_upload";
    }

    @PostMapping("/songs/delete")
    public ResponseEntity<HttpStatus> deleteSong(@RequestParam() String song) {
        File file = new File(songService.UPLOAD_DIR, song);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            file.delete();

            SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
            if (songEntity == null) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            WebServerApplication.database.songCategoryDao.deleteAllCategoriesFromSong(songEntity.id);
            WebServerApplication.database.songPlaylistDao.deleteSongFromAllPlaylists(songEntity.id);
            WebServerApplication.database.songDao.deleteSong(songEntity.id);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/songs/edit")
    public ResponseEntity<HttpStatus> editSong(@RequestParam() String song,
                                                 @RequestParam() String categories) {
        SongEntity songEntity = WebServerApplication.database.songDao.getSong(song);
        if (songEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        WebServerApplication.database.songCategoryDao.deleteAllCategoriesFromSong(songEntity.id);
        for (String category : categories.split(";")) {
            if (category.isEmpty()) {
                continue;
            }
            WebServerApplication.database.songCategoryDao.addCategoryToSong(songEntity.id, category);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/songs/upload")
    public ResponseEntity<HttpStatus> upload(@ModelAttribute SongUploadDto songUploadDto) {
        FileUploadDto fileUploadDto = songUploadDto.getFileUpload();
        if (!songService.isValidFile(fileUploadDto)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        fileUploadDto.setFilename(songService.sanitizeFileName(fileUploadDto.getFilename()));
        String name = fileUploadDto.getFilename();
        File uploadedFile = null;
        try {
            GeneralUtility.uploadFile(fileUploadDto, songService.UPLOAD_DIR);
            uploadedFile  = new File(songService.UPLOAD_DIR, name);

            boolean savedSong = WebServerApplication.database
                    .songDao.saveSong(name,
                            songService.calculateDuration(uploadedFile));
            SongEntity songEntity = WebServerApplication.database.songDao.getSong(name);
            if (songEntity == null || !savedSong) {
                throw new Exception();
            }

            for (String category : songUploadDto.getCategories().split(";")) {
                if (category.isEmpty()) {
                    continue;
                }
                WebServerApplication.database.songCategoryDao.addCategoryToSong(songEntity.id, category);
            }
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
           try {
               uploadedFile.delete();
           } catch (Exception ignored) {}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

