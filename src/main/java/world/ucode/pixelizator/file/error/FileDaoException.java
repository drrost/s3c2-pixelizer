package world.ucode.pixelizator.file.error;

public class FileDaoException extends Exception {

    public FileDaoException(String message) {
        super(message);
    }

    public FileDaoException(String message, Exception e) {
        super(message, e);
    }
}
