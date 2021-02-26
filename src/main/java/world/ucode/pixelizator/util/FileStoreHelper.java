package world.ucode.pixelizator.util;

import org.apache.ibatis.javassist.NotFoundException;

import java.io.File;

public class FileStoreHelper {

    private static final String FILESTORE_FOLDER = "~/Documents/pixelizator";

    public void createFileStoreIfNotExists() throws NotFoundException {
        if (!fileStoreExists())
            initFileStore();
    }

    private boolean fileStoreExists() {
        var path = getFilestoreFolder();
        File rootDirectory = new File(path);
        return rootDirectory.exists();
    }

    private void initFileStore() throws NotFoundException {
        var path = getFilestoreFolder();
        File rootDirectory = new File(path);
        rootDirectory.mkdir();
    }

    private String getFilestoreFolder() {
        var userHome = System.getProperty("user.home");
        return FILESTORE_FOLDER.replace("~", userHome);
    }
}
