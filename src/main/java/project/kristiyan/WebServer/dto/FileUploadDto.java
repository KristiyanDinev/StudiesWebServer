package project.kristiyan.WebServer.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDto {
    private MultipartFile file;
    private String filename;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
