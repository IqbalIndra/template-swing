package com.learn.shirologin;

import com.formdev.flatlaf.FlatLightLaf;
import com.learn.shirologin.ui.main.controller.MainController;
import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShiroLoginApplication {

	public static void main(String[] args) {
                FlatLightLaf.setup();
		ConfigurableApplicationContext context = createApplicationContext(args);
                SwingUtilities.invokeLater(() -> {
                    MainController mainMenuController = context.getBean(MainController.class);
                    mainMenuController.prepareAndOpenFrame();
                });
                
	}

    private static ConfigurableApplicationContext createApplicationContext(String... args) {
        return new SpringApplicationBuilder(ShiroLoginApplication.class)
                .headless(false)
                .run(args);
    }
       
}
