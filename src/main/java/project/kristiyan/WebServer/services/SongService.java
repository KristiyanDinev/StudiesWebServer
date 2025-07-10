package project.kristiyan.WebServer.services;

import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.dto.FileUploadDto;

import java.io.File;

@Service
public class SongService {
    public static final long MAX_FILE_SIZE = 3L * 1024 * 1024 * 1024; // 3 GB
    public static final String UPLOAD_DIR = "song_uploads/";

    public boolean isValidFile(FileUploadDto fileUploadDto) {
        MultipartFile file = fileUploadDto.getFile();
        if (file == null || file.isEmpty() ||
                file.getContentType() == null ||
                !file.getContentType().startsWith("audio/")) {
            return false;
        }
        return file.getSize() <= MAX_FILE_SIZE;
    }

    public String sanitizeFileName(String name) {
        String ext = getExtension(name);
        String cleanName = StringUtils.cleanPath(name).replaceAll("[^a-zA-Z0-9-_\\.]", "_");
        return !cleanName.toLowerCase().endsWith(ext) ? cleanName + "." + ext : cleanName;
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "mp3";
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    public boolean isAudioFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") ||
                name.endsWith(".wav") ||
                name.endsWith(".flac") ||
                name.endsWith(".aac") ||
                name.endsWith(".ogg") ||
                name.endsWith(".m4a");
    }

    public long calculateDuration(File file) {
        try {
            return AudioFileIO.read(file).getAudioHeader().getTrackLength();

        } catch (Exception e) {
            return 0;
        }
    }
}
