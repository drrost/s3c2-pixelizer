package world.ucode.pixelizator.model;

import java.util.UUID;

public class File {

    private UUID id;
    private String name;
    private long size;
    private long timestamp;

    public File(UUID uuid, String name, long size, long timestamp) {
        this.id = uuid;
        this.name = name;
        this.size = size;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
