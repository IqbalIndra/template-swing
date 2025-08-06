package com.learn.shirologin.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
@AllArgsConstructor
@Slf4j
public class IOFile {
    private final File initUploadFolderPath;

    public void createUploadFile(File file,  String filename){
        File fileUpload = new File(initUploadFolderPath, filename);
        try{
            Path path = file.toPath();
            Files.copy(
                    Files.newInputStream(path),
                    fileUpload.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            log.error("Failed to create file cause : {}", e.getMessage());
        }
    }

    public File getUploadFile(String filename){
        File file = new File(initUploadFolderPath, filename);
        if(file.exists())
            return file;
        else
            return null;
    }
}
