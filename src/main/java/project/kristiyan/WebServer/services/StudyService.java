package project.kristiyan.WebServer.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.models.PaginationModel;
import project.kristiyan.WebServer.models.StudyModel;
import project.kristiyan.database.entities.study.StudySeriesEntity;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudyService {
    public static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024; // 5 GB

    @Value("${study_upload_path}")
    public String UPLOAD_DIR;

    @Autowired
    private SearchService searchService;

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

    public PaginationModel<StudyModel> getPage(int page, HttpSession session) {
        File studiesUploadFolder = new File(UPLOAD_DIR);
        PaginationModel<StudyModel> paginationModel = new PaginationModel<>();
        if (!studiesUploadFolder.exists() && !studiesUploadFolder.mkdirs()) {
            return paginationModel;
        }

        List<StudyModel> searchResults = searchService.getStudySessionResults(session);
        if (searchResults != null) {
            List<Object> query = searchService.getStudySearchQuery(session);
            if (query != null) {
                paginationModel.items = searchResults;
                paginationModel.currentPage = page;
                setSearchEngineResults(String.valueOf(query.getFirst()),
                        (List<String>) query.get(1), page, session);
                return paginationModel;
            }
        }

        List<StudyModel> studyModels = new ArrayList<>();
        for (StudySeriesEntity studySeriesEntity :
                WebServerApplication.database.studySeriesDao.getStudies(page)) {
            File study = new File(studiesUploadFolder, studySeriesEntity.study_name);
            if (!study.exists()) {
                continue;
            }
            studyModels.add(new StudyModel(study.getName(),
                    getUploadedDate(study),
                    getStudyFileSize(study),
                    WebServerApplication.database.studySeriesDao
                            .getSeriesFromStudy(studySeriesEntity.study_name)));
        }
        paginationModel.items = studyModels;
        paginationModel.currentPage = page;
        return paginationModel;
    }

    public String getStudyFileSize(File file) {
        return String.format("%.3f", file.length() / (1024.0 * 1024.0));
    }

    public String getUploadedDate(File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class)
                    .creationTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            return "Error reading date";
        }
    }


    public void setSearchEngineResults(String alike_study, List<String> series,
                                       int page, HttpSession session) {
        List<String> foundStudies = WebServerApplication.database
                .studySeriesDao.getSearchEngineResults(alike_study, series, page);

        List<StudyModel> studyModels = new ArrayList<>();
        for (String study : foundStudies) {
            File file = new File(UPLOAD_DIR, study);
            if (!file.exists()) {
                continue;
            }
            studyModels.add(
                    new StudyModel(study,
                            getUploadedDate(file),
                            getStudyFileSize(file),
                            WebServerApplication.database.studySeriesDao
                                    .getSeriesFromStudy(study))
            );
        }
        searchService.setStudySessionResults(studyModels, session);
    }
}
