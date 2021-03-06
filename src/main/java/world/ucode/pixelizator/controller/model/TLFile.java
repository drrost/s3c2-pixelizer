package world.ucode.pixelizator.controller.model;

import world.ucode.pixelizator.model.File;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TLFile {

    public String name;
    public String id;
    public long size;
    public String time;
    public String url;

    public TLFile(File file) {
        name = file.getName();
        id = String.valueOf(file.getId());
        size = file.getSize();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        var date = new Date(file.getTimestamp());
        time = df.format(date);
    }
}
