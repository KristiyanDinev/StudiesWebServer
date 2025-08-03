package project.kristiyan.WebServer.services;

import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.kristiyan.WebServer.WebServerApplication;
import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.models.PaginationModel;
import project.kristiyan.WebServer.models.SermonModel;
import project.kristiyan.database.entities.SermonEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SermonService {
    public static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024; // 5 GB

    @Value("${sermon_items_per_page}")
    public int itemsPerPage;

    @Value("${sermon_upload_path}")
    public String UPLOAD_DIR;

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

    /**
     * Helper method to determine audio content type based on file extension
     */
    public String getAudioContentType(String filename) {
        String extension = filename.toLowerCase().substring(filename.lastIndexOf('.') + 1);
        return switch (extension) {
            case "mp3" -> "audio/mpeg";
            case "wav" -> "audio/wav";
            case "ogg" -> "audio/ogg";
            case "m4a" -> "audio/mp4";
            case "flac" -> "audio/flac";
            case "aac" -> "audio/aac";
            default -> "audio/mpeg"; // Default fallback
        };
    }

    public long calculateDuration(File file) {
        try {
            return AudioFileIO.read(file).getAudioHeader().getTrackLength();

        } catch (Exception e) {
            return 0;
        }
    }

    public PaginationModel<SermonModel> getPage(int page) {
        File sermonUploadFolder = new File(UPLOAD_DIR);
        PaginationModel<SermonModel> paginationModel = new PaginationModel<>();
        if (!sermonUploadFolder.exists() && !sermonUploadFolder.mkdirs()) {
            return paginationModel;
        }

        List<SermonModel> sermonModels = new ArrayList<>();
        for (SermonEntity sermonEntity : WebServerApplication.database
                .sermonDao.getSermonsByPage(page)) {
            File file = new File(sermonUploadFolder, sermonEntity.name);
            if (!file.exists()) {
                continue;
            }
            sermonModels.add(new SermonModel(file, sermonEntity,
                    WebServerApplication.database.sermonCategoryDao
                            .getSermonCategories(sermonEntity.id),
                    WebServerApplication.database.sermonPlaylistDao
                            .getPlaylistsWhereThatSermonIs(sermonEntity.id)));
        }
        paginationModel.setCurrentPage(page);
        paginationModel.setItems(sermonModels);
        return paginationModel;
    }
}
