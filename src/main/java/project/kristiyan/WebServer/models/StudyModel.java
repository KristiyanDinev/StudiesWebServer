package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.study.StudySeriesEntity;

import java.io.Serializable;
import java.util.List;


public class StudyModel implements Serializable {
    public String study_name;
    public String uploadedDate;
    public String fileSize;
    public List<StudySeriesEntity> studySeriesEntities;

    public StudyModel(String study_name, String uploadedDate,
                      String fileSize,
                      List<StudySeriesEntity> studySeriesEntities) {
        this.study_name = study_name;
        this.uploadedDate = uploadedDate;
        this.fileSize = fileSize;
        this.studySeriesEntities = studySeriesEntities;
    }
}
