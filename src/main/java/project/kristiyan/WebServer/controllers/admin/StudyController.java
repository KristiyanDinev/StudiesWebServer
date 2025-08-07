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
import project.kristiyan.WebServer.services.StudyService;
import project.kristiyan.WebServer.utilities.GeneralUtility;

import java.io.File;

@Controller
public class StudyController {

    @Autowired
    public StudyService studyService;


    @GetMapping("/admin/studies")
    public String getStudies(Model model,
                             @RequestParam(defaultValue = "1")
                             int page, HttpSession session) {
        model.addAttribute("studies", studyService.getPage(page, session));
        return "admin/study/studies";
    }


    @GetMapping("/admin/studies/upload")
    public String studyUpload() {
        return "admin/study/study_upload";
    }


    @PostMapping("/admin/studies/delete")
    public ResponseEntity<HttpStatus> deleteStudy(@RequestParam() String study) {
        File file = new File(studyService.UPLOAD_DIR, study);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            file.delete();
            WebServerApplication.database.studySeriesDao.deleteStudy(study);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/admin/studies/upload")
    public ResponseEntity<HttpStatus> upload(@ModelAttribute FileUploadDto fileUploadDto) {
        if (!studyService.isValidFile(fileUploadDto)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        fileUploadDto.setFilename(studyService.sanitizeFileName(fileUploadDto.getFilename()));
        String name = fileUploadDto.getFilename();
        File uploadedFile = null;
        try {
            GeneralUtility.uploadFile(fileUploadDto, studyService.UPLOAD_DIR);
            uploadedFile = new File(studyService.UPLOAD_DIR, name);
            if (!WebServerApplication.database.studySeriesDao.addStudyToSeries(name, null)) {
                throw new Exception();
            }
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            try {
                uploadedFile.delete();
            } catch (Exception ignore2) {
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
