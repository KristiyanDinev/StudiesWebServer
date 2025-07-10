package project.kristiyan.WebServer.dto;

public class SongUploadDto {
    private FileUploadDto fileUpload;
    private String categories;  // separated by ';'

    public String getCategories() {
        return categories;
    }

    public FileUploadDto getFileUpload() {
        return fileUpload;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setFileUpload(FileUploadDto fileUpload) {
        this.fileUpload = fileUpload;
    }
}
