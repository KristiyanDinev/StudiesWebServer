package project.kristiyan.WebServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.dto.SongDto;
import project.kristiyan.WebServer.dto.SongUploadDto;
import project.kristiyan.WebServer.services.SongService;
import project.kristiyan.WebServer.services.StudyService;
import project.kristiyan.WebServer.utilities.FileUtility;
import project.kristiyan.database.entities.SongEntity;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SongController {

    @Autowired
    public SongService songService;

    @Autowired
    public FileUtility fileUtility;

    @GetMapping("/songs")
    public String getSongs(Model model) {
        // get all audio files from that folder
        // set them to model

        File songsUploadFolder = new File(SongService.UPLOAD_DIR);
        List<SongDto> songs = new ArrayList<>();
        if (!songsUploadFolder.exists() && !songsUploadFolder.mkdirs()) {
            model.addAttribute("songs", songs);
            return "song/songs";
        }

        File[] files = songsUploadFolder.listFiles(
                f -> songService.isAudioFile(f) && f.isFile()
        );
        if (files != null) {
            for (File file : files) {
                SongEntity songEntity = WebServerApplication.database
                        .songDao.getSong(file.getName());
                if (songEntity == null) {
                    continue;
                }
                songs.add(new SongDto(file, songEntity));
            }
        }
        model.addAttribute("songs", songs);
        return "song/songs";
    }

    @GetMapping("/songs/upload")
    public String songsUpload() {
        return "song/song_upload";
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
            fileUtility.uploadFile(fileUploadDto, SongService.UPLOAD_DIR);
            uploadedFile  = new File(SongService.UPLOAD_DIR+"/"+name);

            boolean savedSong = WebServerApplication.database
                    .songDao.saveSong(name,
                            songService.calculateDuration(uploadedFile));
            SongEntity songEntity = WebServerApplication.database.songDao.getSong(name);
            if (songEntity == null || !savedSong) {
                if (uploadedFile.exists()) {
                    uploadedFile.delete();
                }
                throw new Exception();
            }

            for (String category : songUploadDto.getCategories().split(";")) {
                WebServerApplication.database.songCategoryDao.addCategoryToSong(songEntity.id, category);
            }
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
           try {
               if (uploadedFile != null && uploadedFile.exists()) {
                   uploadedFile.delete();
               }
           } catch (Exception ignored) {}
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
