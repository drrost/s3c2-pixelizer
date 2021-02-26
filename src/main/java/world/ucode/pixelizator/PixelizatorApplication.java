package world.ucode.pixelizator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import world.ucode.pixelizator.storage.StorageProperties;
import world.ucode.pixelizator.storage.StorageService;
import world.ucode.pixelizator.util.DBHelper;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PixelizatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixelizatorApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            new DBHelper().createDbIfNotExists();
            storageService.deleteAll();
            storageService.init();
        };
    }
}
