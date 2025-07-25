package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.SongCategoryEntity;
import project.kristiyan.database.entities.SongEntity;
import project.kristiyan.database.entities.SongPlaylistEntity;

import java.io.File;
import java.util.List;

public class SongModel {
    private File file;
    private SongEntity songEntity;
    private List<SongCategoryEntity> songCategoryEntities;
    private List<SongPlaylistEntity> songPlaylistEntities;

    public SongModel() {}

    public SongModel(File file, SongEntity songEntity,
                     List<SongCategoryEntity> songCategoryEntities,
                     List<SongPlaylistEntity> songPlaylistEntities) {
        this.file = file;
        this.songEntity = songEntity;
        this.songCategoryEntities = songCategoryEntities;
        this.songPlaylistEntities = songPlaylistEntities;
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
    public List<SongPlaylistEntity> getSongPlaylistEntities() {
        return songPlaylistEntities;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public void setSongCategoryEntities(List<SongCategoryEntity> songCategoryEntities) {
        this.songCategoryEntities = songCategoryEntities;
    }
    public void setSongPlaylistEntities(List<SongPlaylistEntity> songPlaylistEntities) {
        this.songPlaylistEntities = songPlaylistEntities;
    }
    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }
}
