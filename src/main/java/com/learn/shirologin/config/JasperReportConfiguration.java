package com.learn.shirologin.config;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

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
    JasperReport userInfoReport() throws JRException, FileNotFoundException {
        JasperReport jr;
        File f = new File("src/main/resources/report/user-report.jasper");
        if (f.exists()) {
            jr = (JasperReport) JRLoader.loadObject(f);
        } else {
            File file = ResourceUtils.getFile("classpath:report/user-report.jrxml");
            jr = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRSaver.saveObject(jr, "src/main/resources/report/user-report.jasper");
        }
        return jr;
    }
}
