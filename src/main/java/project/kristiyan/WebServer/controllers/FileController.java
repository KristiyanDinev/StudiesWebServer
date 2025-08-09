package project.kristiyan.WebServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import project.kristiyan.WebServer.services.SermonService;
import project.kristiyan.WebServer.services.SongService;
import project.kristiyan.WebServer.services.StudyService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    @Autowired
    public StudyService studyService;

    @Autowired
    public SongService songService;

    @Autowired
    public SermonService sermonService;

    /**
     * Serves PDF files for inline viewing in browser
     * Add ?download=true to force download
     */
    @GetMapping("/file/study/{name}")
    public ResponseEntity<Resource> getStudy(@PathVariable String name,
                                             @RequestParam(defaultValue = "false") boolean download) {
        try {
            Path filePath = Paths.get(studyService.UPLOAD_DIR, name);
            File studyFile = filePath.toFile();

            if (!studyFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Security check
            if (!filePath.normalize().startsWith(Paths.get(studyService.UPLOAD_DIR).normalize())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            Resource resource = new FileSystemResource(studyFile);
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/pdf"; // fallback
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(studyFile.length());

            if (download) {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");
            } else {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + name + "\"");
            }

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    /**
     * Serves audio files for streaming and download
     * Add ?download=true to force download
     */
    @GetMapping("/file/song/{name}")
    public String getSong(@PathVariable String name, Model model) {
        try {
            Path filePath = Paths.get(songService.UPLOAD_DIR, name);
            if (!filePath.toFile().exists()) {
                return "home/error";
            }
            // Security check - ensure file is within upload directory
            if (!filePath.normalize().startsWith(Paths.get(songService.UPLOAD_DIR).normalize())) {
                return "home/error";
            }
            model.addAttribute("song", name);
            model.addAttribute("type", songService.getAudioContentType(name));
            return "home/song/song_download";

        } catch (Exception ignore) {
            return "home/error";
        }
    }

    @GetMapping("/file/song/download/{name}")
    public ResponseEntity<Resource> downloadSong(@PathVariable String name) {
        try {
            Path filePath = Paths.get(songService.UPLOAD_DIR, name);
            File songFile = filePath.toFile();
            if (!songFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            // Security check - ensure file is within upload directory
            if (!filePath.normalize().startsWith(Paths.get(songService.UPLOAD_DIR).normalize())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(songService.getAudioContentType(name)));
            headers.setContentLength(songFile.length());
            // Enable range requests for audio streaming
            headers.set("Accept-Ranges", "bytes");
            headers.setContentDispositionFormData("attachment", name);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(songFile));

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    /**
     * Serves audio files for streaming and download
     * Add ?download=true to force download
     */
    @GetMapping("/file/sermon/{name}")
    public String getSermon(@PathVariable String name, Model model) {
        try {
            Path filePath = Paths.get(sermonService.UPLOAD_DIR, name);
            if (!filePath.toFile().exists()) {
                return "home/error";
            }
            // Security check - ensure file is within upload directory
            if (!filePath.normalize().startsWith(Paths.get(sermonService.UPLOAD_DIR).normalize())) {
                return "home/error";
            }
            model.addAttribute("sermon", name);
            model.addAttribute("type", sermonService.getAudioContentType(name));
            return "home/sermon/sermon_download";

        } catch (Exception ignore) {
            return "home/error";
        }
    }

    @GetMapping("/file/sermon/download/{name}")
    public ResponseEntity<Resource> downloadSermon(@PathVariable String name) {
        try {
            Path filePath = Paths.get(sermonService.UPLOAD_DIR, name);
            File songFile = filePath.toFile();
            if (!songFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            // Security check - ensure file is within upload directory
            if (!filePath.normalize().startsWith(Paths.get(sermonService.UPLOAD_DIR).normalize())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(sermonService.getAudioContentType(name)));
            headers.setContentLength(songFile.length());
            // Enable range requests for audio streaming
            headers.set("Accept-Ranges", "bytes");
            headers.setContentDispositionFormData("attachment", name);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new FileSystemResource(songFile));

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
