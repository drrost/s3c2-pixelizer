package world.ucode.pixelizator.services.model;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class FSFileModel {

    public abstract InputStream getInputStream() throws IOException;
    public abstract long getSize();
    public abstract String getName();

    private static class MultipartFileBased extends FSFileModel {

        private MultipartFile multipartFile;

        public MultipartFileBased(MultipartFile multipartFile) {
            this.multipartFile = multipartFile;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return multipartFile.getInputStream();
        }

        @Override
        public long getSize() {
            return multipartFile.getSize();
        }

        @Override
        public String getName() {
            return multipartFile.getOriginalFilename();
        }
    }

    private static class BufferedImageBased extends FSFileModel {

        private InputStream inputStream;
        private long size;
        private String fileName;

        public BufferedImageBased(BufferedImage bufferedImage) throws IOException {
            var outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", outputStream);
            size = outputStream.size();
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return inputStream;
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public String getName() {
            return fileName;
        }
    }

    private FSFileModel() {
    }

    public static FSFileModel create(MultipartFile multipartFile) {
        return new MultipartFileBased(multipartFile);
    }

    public static FSFileModel create(BufferedImage bufferedImage, String name) throws IOException {
        return new BufferedImageBased(bufferedImage);
    }
}
