package project.kristiyan.WebServer.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.dto.FileUploadDto;

@Service
public class StudyService {
    public static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024; // 5 GB
    public static final String UPLOAD_DIR = "study_uploads/";

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
}
