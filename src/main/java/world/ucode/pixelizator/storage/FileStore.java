package world.ucode.pixelizator.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStore {

    void init();

    void store(MultipartFile file, String fileId);

    Stream<Path> loadAll();

    Path load(String uuid);

    Resource loadAsResource(String uuid);

    void deleteAll();
}
