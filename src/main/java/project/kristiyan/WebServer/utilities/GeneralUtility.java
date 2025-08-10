package project.kristiyan.WebServer.utilities;

import project.kristiyan.WebServer.dto.FileUploadDto;
import project.kristiyan.WebServer.models.SermonModel;
import project.kristiyan.WebServer.models.SongModel;
import project.kristiyan.WebServer.models.StudyModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class GeneralUtility {

    public static boolean LogsEnabled = Boolean.parseBoolean(System.getenv("WEB_LOGS"));

    private static final Logger _logger = Logger.getLogger("Action Log");

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void uploadFile(FileUploadDto fileUploadDto, String upload_dir) throws IOException {
        Path uploadPath = Paths.get(upload_dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(fileUploadDto.getFile().getInputStream(),
                uploadPath.resolve(fileUploadDto.getFilename()),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public static String getFileSize(File file) {
        return String.format("%.3f", file.length() / (1024.0 * 1024.0));
    }

    public static String getUploadedDate(File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class)
                    .creationTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .format(dateTimeFormatter);
        } catch (Exception e) {
            return "Error reading date";
        }
    }

    public static Stream<StudyModel> sortByLatestUpload_Study(Stream<StudyModel> stream) {
        return stream.sorted((s1, s2) ->
                s2.uploadedDate.compareTo(s1.uploadedDate)
        );
    }

    public static Stream<SongModel> sortByLatestUpload_Song(Stream<SongModel> stream) {
        return stream.sorted((s1, s2) ->
                s2.uploadedDate.compareTo(s1.uploadedDate)
        );
    }

    public static Stream<SermonModel> sortByLatestUpload_Sermon(Stream<SermonModel> stream) {
        return stream.sorted((s1, s2) ->
                s2.uploadedDate.compareTo(s1.uploadedDate)
        );
    }

    public static void logMessage(String message) {
        if (LogsEnabled) {
            _logger.info(message);
        }
    }
}
