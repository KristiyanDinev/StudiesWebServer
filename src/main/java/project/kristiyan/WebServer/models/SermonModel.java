package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.sermon.SermonCategoryEntity;
import project.kristiyan.database.entities.sermon.SermonEntity;
import project.kristiyan.database.entities.sermon.SermonPlaylistEntity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SermonModel implements Serializable {
    public File file;
    public SermonEntity sermonEntity;
    public List<SermonCategoryEntity> sermonCategoryEntities;
    public List<SermonPlaylistEntity> sermonPlaylistEntities;

    public SermonModel() {
    }

    public SermonModel(File file, SermonEntity sermonEntity,
                       List<SermonCategoryEntity> sermonCategoryEntities,
                       List<SermonPlaylistEntity> sermonPlaylistEntities) {
        this.file = file;
        this.sermonEntity = sermonEntity;
        this.sermonCategoryEntities = sermonCategoryEntities;
        this.sermonPlaylistEntities = sermonPlaylistEntities;
    }
}
