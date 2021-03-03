package world.ucode.pixelizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import world.ucode.pixelizator.file.FileDao;
import world.ucode.pixelizator.storage.StorageService;
import world.ucode.pixelizator.util.DBHelper;

@Service
public class FileService {

    private FileDao fileDao;
    private StorageService storageService;

    @Autowired
    public FileService(FileDao fileDao, StorageService storageService) {
        this.fileDao = fileDao;
        this.storageService = storageService;
    }

    public void init() {
        new DBHelper().createDbIfNotExists();
        storageService.init();
    }
}
