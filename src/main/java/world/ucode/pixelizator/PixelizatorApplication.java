package world.ucode.pixelizator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import world.ucode.pixelizator.util.DBHelper;

@SpringBootApplication
public class PixelizatorApplication {

    public static void main(String[] args) {
        new DBHelper().createDbIfNotExists();
        SpringApplication.run(PixelizatorApplication.class, args);
    }
}
