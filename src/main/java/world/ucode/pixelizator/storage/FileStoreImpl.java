package world.ucode.pixelizator.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.pixelizator.storage.exceptions.FileStoreException;
import world.ucode.pixelizator.storage.exceptions.FileStoreFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileStoreImpl implements FileStore {

    private final Path rootLocation;

    @Autowired
    public FileStoreImpl(FileStoreProperties properties) {
        rootLocation = Paths.get(properties.getLocation());
    }

    // FileStore

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStoreException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(InputStream inputStream, String uuid) {
        try {
            if (inputStream.available() == 0) {
                throw new FileStoreException("Failed to store empty file.");
            }
            createFolderIfNeeded(uuid);

            Path destinationFile = getPath(uuid);

            if (!destinationFile.getParent().startsWith(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new FileStoreException(
                    "Cannot store file outside current directory.");
            }
            Files.copy(inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStoreException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                .filter(path -> !path.equals(rootLocation))
                .map(rootLocation::relativize);
        } catch (IOException e) {
            throw new FileStoreException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String uuid) {
        return getPath(uuid);
    }

    @Override
    public Resource loadAsResource(String uuid) {
        try {
            Path file = load(uuid);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStoreFileNotFoundException(
                    "Could not read file: " + uuid);
            }
        } catch (MalformedURLException e) {
            throw new FileStoreFileNotFoundException("Could not read file: " + uuid, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    // Private

    private Path getPath(String fileId) {
        Path destinationFolder = getDestinationFolder(fileId);
        Path destinationFile = destinationFolder.resolve(
            Paths.get(fileId))
            .normalize().toAbsolutePath();
        return destinationFile;
    }

    private Path getDestinationFolder(String fileId) {
        var uuidFolder = UuidPathConverter.path(fileId, 3);
        return rootLocation.resolve(uuidFolder);
    }

    private void createFolderIfNeeded(String uuid) {
        Path path = getDestinationFolder(uuid);
        if (!Files.exists(path)) {
            var file = new File(String.valueOf(path));
            if (!file.mkdirs()) {
                throw new FileStoreException("Can't create folder: \"" + path + "\"");
            }
        }
    }
}
