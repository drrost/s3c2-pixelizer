package world.ucode.pixelizator.file;

import world.ucode.pixelizator.file.error.FileDaoException;
import world.ucode.pixelizator.file.error.NotFoundException;
import world.ucode.pixelizator.model.File;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileDaoInMemory implements FileDao {

    private List<File> fileList;

    public FileDaoInMemory() {
        fileList = new ArrayList<>();
    }

    @Override
    public void createFile(File file) {
        file.setId(UUID.randomUUID());
        fileList.add(file);
    }

    @Override
    public File fileById(UUID id) throws FileDaoException {
        for (File file : fileList) {
            if (file.getId() == id)
                return file;
        }
        throw new NotFoundException("File with id " + id + " not found");
    }

    @Override
    public List<File> findAll() throws FileDaoException {
        return fileList;
    }

    @Override
    public void update(File file) throws FileDaoException {
        File inFile = fileById(file.getId());
        inFile.setName(file.getName());
        inFile.setSize(file.getSize());
    }

    @Override
    public void deleteFile(UUID id) throws FileDaoException {
        fileList.stream().filter(a -> a.getId() != id);
    }
}
