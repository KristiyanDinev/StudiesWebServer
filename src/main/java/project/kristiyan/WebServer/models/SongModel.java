package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.song.SongCategoryEntity;
import project.kristiyan.database.entities.song.SongEntity;
import project.kristiyan.database.entities.song.SongPlaylistEntity;

import java.io.Serializable;
import java.util.List;

public class SongModel implements Serializable {
    public String uploadedDate;
    public String fileSize;
    public SongEntity songEntity;
    public List<SongCategoryEntity> songCategoryEntities;
    public List<SongPlaylistEntity> songPlaylistEntities;

    public SongModel() {
    }

    public SongModel(String uploadedDate, String fileSize,
                     SongEntity songEntity,
                     List<SongCategoryEntity> songCategoryEntities,
                     List<SongPlaylistEntity> songPlaylistEntities) {
        this.uploadedDate = uploadedDate;
        this.fileSize = fileSize;
        this.songEntity = songEntity;
        this.songCategoryEntities = songCategoryEntities;
        this.songPlaylistEntities = songPlaylistEntities;
    }
}
