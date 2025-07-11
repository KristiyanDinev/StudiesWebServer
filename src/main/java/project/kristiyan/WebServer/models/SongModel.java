package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.SongCategoryEntity;
import project.kristiyan.database.entities.SongEntity;

import java.io.File;
import java.util.List;

public class SongModel {
    private File file;
    private SongEntity songEntity;
    private List<SongCategoryEntity> songCategoryEntities;

    public SongModel() {}

    public SongModel(File file, SongEntity songEntity, List<SongCategoryEntity> songCategoryEntities) {
        this.file = file;
        this.songEntity = songEntity;
        this.songCategoryEntities = songCategoryEntities;
    }

    public File getFile() {
        return file;
    }
    public SongEntity getSongEntity() {
        return songEntity;
    }
    public List<SongCategoryEntity> getSongCategoryEntities() {
        return songCategoryEntities;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public void setSongCategoryEntities(List<SongCategoryEntity> songCategoryEntities) {
        this.songCategoryEntities = songCategoryEntities;
    }
    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }
}
