package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.StudySeriesEntity;

import java.io.File;
import java.util.List;

public class StudyModel {
    private File file;
    private List<StudySeriesEntity> studySeriesEntities;

    public StudyModel(File file, List<StudySeriesEntity> studySeriesEntities) {
        this.file = file;
        this.studySeriesEntities = studySeriesEntities;
    }

    public File getFile() {
        return file;
    }

    public List<StudySeriesEntity> getStudySeriesEntities() {
        return studySeriesEntities;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setStudySeriesEntities(List<StudySeriesEntity> studySeriesEntities) {
        this.studySeriesEntities = studySeriesEntities;
    }
}
