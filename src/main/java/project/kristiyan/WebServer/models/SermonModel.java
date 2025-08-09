package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.sermon.SermonCategoryEntity;
import project.kristiyan.database.entities.sermon.SermonEntity;
import project.kristiyan.database.entities.sermon.SermonPlaylistEntity;

import java.io.Serializable;
import java.util.List;

public class SermonModel implements Serializable {
    public String uploadedDate;
    public String fileSize;
    public SermonEntity sermonEntity;
    public List<SermonCategoryEntity> sermonCategoryEntities;
    public List<SermonPlaylistEntity> sermonPlaylistEntities;

    public SermonModel() {
    }

    public SermonModel(String uploadedDate, String fileSize,
                       SermonEntity sermonEntity,
                       List<SermonCategoryEntity> sermonCategoryEntities,
                       List<SermonPlaylistEntity> sermonPlaylistEntities) {
        this.uploadedDate = uploadedDate;
        this.fileSize = fileSize;
        this.sermonEntity = sermonEntity;
        this.sermonCategoryEntities = sermonCategoryEntities;
        this.sermonPlaylistEntities = sermonPlaylistEntities;
    }
}
