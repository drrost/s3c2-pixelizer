package world.ucode.pixelizator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.storage.FileStoreProperties;
import world.ucode.pixelizator.storage.FileStore;

@SpringBootApplication
@EnableConfigurationProperties(FileStoreProperties.class)
public class PixelizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixelizatorApplication.class, args);
    }

    // More details here: https://www.baeldung.com/running-setup-logic-on-startup-in-spring
    // section 2.6
    //
    @Bean
    CommandLineRunner init(FileStore fileStore, FileService fileService) {
        return (args) -> {
            fileService.init();
        };
    }
}
