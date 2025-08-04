package project.kristiyan.WebServer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.models.PaginationModel;
import project.kristiyan.WebServer.models.StudyModel;
import project.kristiyan.database.entities.StudySeriesEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudyService {
    public static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024; // 5 GB

    @Value("${study_items_per_page}")
    public int itemsPerPage;

    @Value("${study_upload_path}")
    public String UPLOAD_DIR;

    public boolean isValidFile(FileUploadDto fileUploadDto) {
        MultipartFile file = fileUploadDto.getFile();
        if ((file == null || file.isEmpty()) ||
                !"application/pdf".equals(file.getContentType())) {
            return false;
        }
        return file.getSize() <= MAX_FILE_SIZE;
    }

    public String sanitizeFileName(String name) {
        String cleanName = StringUtils.cleanPath(name).replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        return !cleanName.toLowerCase().endsWith(".pdf") ? cleanName + ".pdf" : cleanName;
    }

    public PaginationModel<StudyModel> getPage(int page) {
        File studiesUploadFolder = new File(UPLOAD_DIR);
        PaginationModel<StudyModel> paginationModel = new PaginationModel<>();
        if (!studiesUploadFolder.exists() && !studiesUploadFolder.mkdirs()) {
            return paginationModel;
        }

        List<StudyModel> studyModels = new ArrayList<>();
        for (StudySeriesEntity studySeriesEntity :
                WebServerApplication.database.studySeriesDao.getStudies(page)) {
            File study = new File(studiesUploadFolder, studySeriesEntity.study_name);
            if (!study.exists()) {
                continue;
            }
            studyModels.add(new StudyModel(study,
                    WebServerApplication.database.studySeriesDao
                            .getSeriesFromStudy(studySeriesEntity.study_name, 1)));
        }
        paginationModel.setItems(studyModels);
        paginationModel.setCurrentPage(page);
        return paginationModel;
    }
}
