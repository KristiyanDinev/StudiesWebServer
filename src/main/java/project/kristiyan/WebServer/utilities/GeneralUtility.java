package project.kristiyan.WebServer.utilities;

import project.kristiyan.WebServer.dto.FileUploadDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GeneralUtility {

    public static void uploadFile(FileUploadDto fileUploadDto, String upload_dir) throws IOException {
        Path uploadPath = Paths.get(upload_dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(fileUploadDto.getFile().getInputStream(),
                uploadPath.resolve(fileUploadDto.getFilename()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
