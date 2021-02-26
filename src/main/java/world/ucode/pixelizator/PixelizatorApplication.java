package world.ucode.pixelizator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import world.ucode.pixelizator.util.DBHelper;
import world.ucode.pixelizator.util.FileStoreHelper;

@SpringBootApplication
public class PixelizatorApplication {

    public static void main(String[] args) {
        new DBHelper().createDbIfNotExists();
        try {
            new FileStoreHelper().createFileStoreIfNotExists();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        SpringApplication.run(PixelizatorApplication.class, args);
    }
}
