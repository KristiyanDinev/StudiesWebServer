package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.StudySeriesEntity;

import java.util.List;

public class StudyModel {
    private String study_name;
    private String uploadedDate;
    private String fileSize;
    private List<StudySeriesEntity> studySeriesEntities;

    public StudyModel(String study_name, String uploadedDate,
                      String fileSize,
                      List<StudySeriesEntity> studySeriesEntities) {
        this.study_name = study_name;
        this.uploadedDate = uploadedDate;
        this.fileSize = fileSize;
        this.studySeriesEntities = studySeriesEntities;
    }

    public String getStudyName() {
        return study_name;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public String getFileSize() {
        return fileSize;
    }

    public List<StudySeriesEntity> getStudySeriesEntities() {
        return studySeriesEntities;
    }

    public void setStudy_name(String study_name) {
        this.study_name = study_name;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setStudySeriesEntities(List<StudySeriesEntity> studySeriesEntities) {
        this.studySeriesEntities = studySeriesEntities;
    }

}
