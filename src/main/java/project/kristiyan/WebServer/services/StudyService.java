package project.kristiyan.WebServer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.models.PaginationModel;
import project.kristiyan.WebServer.models.StudyModel;

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
        File[] allFiles = studiesUploadFolder.listFiles(
                f -> f.getName().endsWith(".pdf") && f.isFile()
        );
        if (allFiles == null || allFiles.length == 0) {
            return paginationModel;
        }

        Arrays.sort(allFiles, (a, b) -> a.getName().compareTo(b.getName()));
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, allFiles.length);

        if (startIndex >= allFiles.length) {
            return paginationModel;
        }

        List<StudyModel> studyModels = new ArrayList<>();
        for (File study : Arrays.copyOfRange(allFiles, startIndex, endIndex)) {
            studyModels.add(new StudyModel(study,
                    WebServerApplication.database.studySeriesDao
                            .getSeriesFromStudy(study.getName())));
        }
        paginationModel.setItems(studyModels);
        paginationModel.setCurrentPage(page);
        paginationModel.setTotalPages((int) Math.ceil((double) allFiles.length / itemsPerPage));
        return paginationModel;
    }
}
