package project.kristiyan.WebServer.controllers.admin;

import jakarta.servlet.http.HttpSession;
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
import project.kristiyan.WebServer.services.SermonService;
import project.kristiyan.WebServer.utilities.GeneralUtility;
import project.kristiyan.database.entities.sermon.SermonEntity;

import java.io.File;

@Controller
public class SermonController {

    @Autowired
    public SermonService sermonService;

    @GetMapping("/admin/sermons")
    public String getSermons(Model model,
                             @RequestParam(defaultValue = "1")
                             int page,
                             HttpSession session) {
        model.addAttribute("sermons", sermonService.getPage(page, session));
        return "admin/sermon/sermons";
    }

    @GetMapping("/admin/sermons/upload")
    public String sermonsUpload() {
        return "admin/sermon/sermon_upload";
    }

    @PostMapping("/admin/sermons/delete")
    public ResponseEntity<HttpStatus> deleteSermon(@RequestParam() String sermon) {
        File file = new File(sermonService.UPLOAD_DIR, sermon);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            file.delete();

            SermonEntity sermonEntity = WebServerApplication.database.sermonDao.getSermon(sermon);
            if (sermonEntity == null) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            WebServerApplication.database.sermonCategoryDao.deleteAllCategoriesFromSermon(sermonEntity.id);
            WebServerApplication.database.sermonPlaylistDao.deleteSermonFromAllPlaylists(sermonEntity.id);
            WebServerApplication.database.sermonDao.deleteSermon(sermonEntity.id);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/admin/sermons/edit")
    public ResponseEntity<HttpStatus> editSermon(@RequestParam()
                                                 String sermon,
                                                 @RequestParam() String categories) {
        SermonEntity sermonEntity = WebServerApplication.database.sermonDao.getSermon(sermon);
        if (sermonEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        WebServerApplication.database.sermonCategoryDao.deleteAllCategoriesFromSermon(sermonEntity.id);
        for (String category : categories.split(";")) {
            if (category.isEmpty()) {
                continue;
            }
            WebServerApplication.database.sermonCategoryDao
                    .addCategoryToSermon(sermonEntity.id, category.replace(" ", "-"));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/admin/sermons/upload")
    public ResponseEntity<HttpStatus> uploadSermon(@ModelAttribute SongUploadDto songUploadDto) {
        FileUploadDto fileUploadDto = songUploadDto.getFileUpload();
        if (!sermonService.isValidFile(fileUploadDto)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        fileUploadDto.setFilename(sermonService.sanitizeFileName(fileUploadDto.getFilename()));
        String name = fileUploadDto.getFilename();
        File uploadedFile = new File(sermonService.UPLOAD_DIR, name);
        try {
            boolean existedBefore = uploadedFile.exists();
            GeneralUtility.uploadFile(fileUploadDto, sermonService.UPLOAD_DIR);
            if (existedBefore) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            boolean savedSong = WebServerApplication.database
                    .sermonDao.saveSermon(name,
                            sermonService.calculateDuration(uploadedFile));
            SermonEntity sermonEntity = WebServerApplication.database.sermonDao.getSermon(name);
            if (sermonEntity == null || !savedSong) {
                throw new Exception();
            }

            for (String category : songUploadDto.getCategories().split(";")) {
                if (category.isEmpty()) {
                    continue;
                }
                WebServerApplication.database.sermonCategoryDao
                        .addCategoryToSermon(sermonEntity.id, category.replace(" ", "-"));
            }
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            try {
                uploadedFile.delete();
            } catch (Exception ignored) {
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

