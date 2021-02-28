package world.ucode.pixelizator.file;

import world.ucode.pixelizator.file.error.FileDaoException;
import world.ucode.pixelizator.model.File;

import java.util.List;
import java.util.UUID;

public interface FileDao {

    // C
    void createFile(File file) throws FileDaoException;

    // R
    File fileById(UUID id) throws FileDaoException;
    List<File> findAll() throws FileDaoException;

    // U
    void update(File file) throws FileDaoException;

    // D
    void deleteFile(UUID id) throws FileDaoException;
}
