package world.ucode.pixelizator.storage.exceptions;

public class FileStoreFileNotFoundException extends FileStoreException {

    public FileStoreFileNotFoundException(String message) {
        super(message);
    }

    public FileStoreFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
