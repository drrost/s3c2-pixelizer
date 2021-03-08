package world.ucode.pixelizator.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStore {

    void init();

    void store(InputStream inputStream, String uuid);

    Stream<Path> loadAll();

    Path load(String uuid);

    Resource loadAsResource(String uuid);

    void deleteAll();
}
