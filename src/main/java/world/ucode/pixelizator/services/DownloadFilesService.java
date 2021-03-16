package world.ucode.pixelizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.pixelizator.storage.FileStoreProperties;
import world.ucode.pixelizator.storage.exceptions.FileStoreException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class DownloadFilesService {

    private final Path rootLocation;

    @Autowired
    public DownloadFilesService(FileStoreProperties properties) {
        rootLocation = Paths.get(properties.getLocation() + "/downloads");
        createFolderIfNeeded(rootLocation);
    }

    public MultipartFile download(String url) throws IOException {

        URL website = new URL(url);

        try {
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());

            var fileName = UUID.randomUUID().toString();
            var filePath = rootLocation + "/" + fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();

            FileInputStream fin = new FileInputStream(filePath);

            var multipartFile = new MockMultipartFile(fileName, fileName, "", fin);
            fin.close();

            return multipartFile;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteFile(String name) {
        String path = String.valueOf(rootLocation) + "/" + name;
        File file = new File(path);
        file.delete();
    }

    private void createFolderIfNeeded(Path path) {
        if (!Files.exists(path)) {
            var file = new File(String.valueOf(path));
            if (!file.mkdirs()) {
                throw new FileStoreException("Can't create folder: \"" + path + "\"");
            }
        }
    }
}
