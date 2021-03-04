package world.ucode.pixelizator.controller.model;

import world.ucode.pixelizator.model.File;

public class TLFile {

    public String name;
    public String id;
    public long size;
    public String url;

    public TLFile(File file) {
        name = file.getName();
        id = String.valueOf(file.getId());
        size = file.getSize();
    }
}
