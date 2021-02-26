package world.ucode.pixelizator.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private static final String FILESTORE_FOLDER = "~/Documents/pixelizator";

    private String location;

    public StorageProperties() {
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
