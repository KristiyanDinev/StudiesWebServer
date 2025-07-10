package project.kristiyan.WebServer.dto;

import project.kristiyan.database.entities.SongEntity;

import java.io.File;

public class SongDto {
    private File file;
    private SongEntity songEntity;

    public SongDto() {}

    public SongDto(File file, SongEntity songEntity) {
        this.file = file;
        this.songEntity = songEntity;
    }

    public File getFile() {
        return file;
    }

    public SongEntity getSongEntity() {
        return songEntity;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }
}
