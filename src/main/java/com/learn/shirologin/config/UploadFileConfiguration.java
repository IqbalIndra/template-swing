package com.learn.shirologin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class UploadFileConfiguration {

    @Bean
    public File initUploadFolderPath(
            @Value("${upload.path}") String uploadPath) {
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}
