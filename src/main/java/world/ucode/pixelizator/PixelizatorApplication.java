package world.ucode.pixelizator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.storage.StorageProperties;
import world.ucode.pixelizator.storage.StorageService;
import world.ucode.pixelizator.util.DBHelper;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PixelizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixelizatorApplication.class, args);
    }

    // More details here: https://www.baeldung.com/running-setup-logic-on-startup-in-spring
    // section 2.6
    //
    @Bean
    CommandLineRunner init(StorageService storageService, FileService fileService) {
        return (args) -> {
            fileService.init();
        };
    }
}
