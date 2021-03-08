package world.ucode.pixelizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.pixelizator.dao.FileDao;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.model.File;
import world.ucode.pixelizator.services.model.FSFileModel;
import world.ucode.pixelizator.services.model.ResourceFileModel;
import world.ucode.pixelizator.storage.FileStore;
import world.ucode.pixelizator.util.DBHelper;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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

    public File add(FSFileModel model) throws FileDaoException, IOException {
        var timestamp = Instant.now().toEpochMilli();

        var file = new File(UUID.randomUUID(), model.getName(), model.getSize(), timestamp);

        fileStore.store(model.getInputStream(), String.valueOf(file.getId()));
        fileDao.createFile(file);

        return file;
    }

    public List<File> all() throws FileDaoException {
        return fileDao.findAll();
    }

    public ResourceFileModel load(String uuid) throws FileDaoException {
        var file = fileDao.fileById(UUID.fromString(uuid));
        var resource = fileStore.loadAsResource(uuid);

        var fsFile = new ResourceFileModel(resource, file.getName());

        return fsFile;
    }
}
