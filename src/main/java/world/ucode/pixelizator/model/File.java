package world.ucode.pixelizator.model;

import java.util.UUID;

public class File {

    private UUID id;
    private String name;
    private long size;

    public File(UUID uuid, String name, long size) {
        this.id = uuid;
        this.name = name;
        this.size = size;
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
}
