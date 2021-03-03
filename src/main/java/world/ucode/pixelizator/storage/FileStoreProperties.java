package world.ucode.pixelizator.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class FileStoreProperties {

    private static final String FILESTORE_FOLDER = "~/Documents/pixelizator";

    private String location;

    public FileStoreProperties() {
        var userHome = System.getProperty("user.home");
        location = FILESTORE_FOLDER.replace("~", userHome);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
