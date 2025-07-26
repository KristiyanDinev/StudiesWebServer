package project.kristiyan.WebServer.models;

import project.kristiyan.database.entities.*;

import java.io.File;
import java.util.List;

public class SermonModel {
    private File file;
    private SermonEntity sermonEntity;
    private List<SermonCategoryEntity> sermonCategoryEntities;
    private List<SermonPlaylistEntity> sermonPlaylistEntities;

    public SermonModel() {}

    public SermonModel(File file, SermonEntity sermonEntity,
                       List<SermonCategoryEntity> sermonCategoryEntities,
                       List<SermonPlaylistEntity> sermonPlaylistEntities) {
        this.file = file;
        this.sermonEntity = sermonEntity;
        this.sermonCategoryEntities = sermonCategoryEntities;
        this.sermonPlaylistEntities = sermonPlaylistEntities;
    }

    public File getFile() {
        return file;
    }
    public SermonEntity getSermonEntity() {
        return sermonEntity;
    }
    public List<SermonCategoryEntity> getSermonCategoryEntities() {
        return sermonCategoryEntities;
    }
    public List<SermonPlaylistEntity> getSermonPlaylistEntities() {
        return sermonPlaylistEntities;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public void setSermonCategoryEntities(List<SermonCategoryEntity> sermonCategoryEntities) {
        this.sermonCategoryEntities = sermonCategoryEntities;
    }
    public void setSermonPlaylistEntities(List<SermonPlaylistEntity> sermonPlaylistEntities) {
        this.sermonPlaylistEntities = sermonPlaylistEntities;
    }
    public void setSermonEntity(SermonEntity sermonEntity) {
        this.sermonEntity = sermonEntity;
    }
}
