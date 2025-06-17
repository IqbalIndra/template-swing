package com.learn.shirologin.config;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;

@Configuration
public class JasperReportConfiguration {

    @Value("${directory}")
    private String directory;

    @Bean
    JRFileVirtualizer fileVirtualizer() {
        return new JRFileVirtualizer(100, directory);
    }

    @Bean
    JRSwapFileVirtualizer swapFileVirtualizer() {
        JRSwapFile sf = new JRSwapFile(directory, 1024, 100);
        return new JRSwapFileVirtualizer(20, sf, true);
    }

    @Bean
    public File initReportFolderPath(
            @Value("${report.path}") String reportPath) {
        File directory = new File(reportPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }else{
            directory.delete();
        }
        return directory;
    }

    @Bean
    @DependsOn({"initReportFolderPath"})
    JasperReport userInfoReport(@Value("${report.path}") String reportPath) throws JRException, IOException {
        JasperReport jr;
        String separator = File.separator;

        File file = new File(reportPath+separator+"user-report.jasper");
        if (file.exists()) {
            InputStream inputStream = Files.newInputStream(file.toPath());
            jr = (JasperReport) JRLoader.loadObject(inputStream);
        } else {
            ClassPathResource resource = new ClassPathResource("report/user-report.jrxml");
            jr = JasperCompileManager.compileReport(resource.getInputStream());

            JRSaver.saveObject(jr, reportPath+separator+"user-report.jasper");
        }
        return jr;
    }
}
