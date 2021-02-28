package world.ucode.pixelizator.file.error;

public class NotFoundException extends FileDaoException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Exception e) {
        super(message, e);
    }
}
