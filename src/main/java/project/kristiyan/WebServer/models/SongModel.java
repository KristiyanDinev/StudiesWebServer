package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.song.SongCategoryEntity;
import project.kristiyan.database.entities.song.SongEntity;
import project.kristiyan.database.entities.song.SongPlaylistEntity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SongModel implements Serializable {
    public File file;
    public SongEntity songEntity;
    public List<SongCategoryEntity> songCategoryEntities;
    public List<SongPlaylistEntity> songPlaylistEntities;

    public SongModel() {
    }

    public SongModel(File file, SongEntity songEntity,
                     List<SongCategoryEntity> songCategoryEntities,
                     List<SongPlaylistEntity> songPlaylistEntities) {
        this.file = file;
        this.songEntity = songEntity;
        this.songCategoryEntities = songCategoryEntities;
        this.songPlaylistEntities = songPlaylistEntities;
    }
}
