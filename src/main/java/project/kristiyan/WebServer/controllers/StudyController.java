package project.kristiyan.WebServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.services.StudyService;
import project.kristiyan.WebServer.utilities.FileUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudyController {

    @Autowired
    public StudyService studyService;

    @Autowired
    public FileUtility fileUtility;

    @GetMapping("/studies")
    public String getStudies(Model model) {

        // get all pdf files from that folder
        // set them to model

        File studiesUploadFolder = new File(StudyService.UPLOAD_DIR);
        List<File> studies = new ArrayList<>();
        if (!studiesUploadFolder.exists() && !studiesUploadFolder.mkdirs()) {
            model.addAttribute("studies", studies);
            return "study/studies";
        }

        File[] files = studiesUploadFolder.listFiles(
                f -> f.getName().endsWith(".pdf") && f.isFile()
        );
        if (files != null && files.length > 0) {
            studies.addAll(Arrays.stream(files).toList());
        }
        model.addAttribute("studies", studies);
        return "study/studies";
    }

    @GetMapping("/studies/upload")
    public String studyUpload() {
        return "study/study_upload";
    }

    @PostMapping("/studies/upload")
    public ResponseEntity<HttpStatus> upload(@ModelAttribute FileUploadDto fileUploadDto) {
        if (!studyService.isValidFile(fileUploadDto)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        fileUploadDto.setFilename(studyService.sanitizeFileName(fileUploadDto.getFilename()));
        try {
            fileUtility.uploadFile(fileUploadDto, StudyService.UPLOAD_DIR);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception ignore) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
