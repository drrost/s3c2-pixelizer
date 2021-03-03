package world.ucode.pixelizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.ucode.pixelizator.file.FileDao;
import world.ucode.pixelizator.storage.FileStore;
import world.ucode.pixelizator.util.DBHelper;

@Service
public class FileService {

    private FileDao fileDao;
    private FileStore fileStore;

    @Autowired
    public FileService(FileDao fileDao, FileStore fileStore) {
        this.fileDao = fileDao;
        this.fileStore = fileStore;
    }

    public void init() {
        new DBHelper().createDbIfNotExists();
        fileStore.init();
    }
}
