package world.ucode.pixelizator.controller.model;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import world.ucode.pixelizator.controller.FileController;
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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var date = new Date(file.getTimestamp());
        time = df.format(date);

        var uuid = String.valueOf(file.getId());
        var uri = MvcUriComponentsBuilder.fromMethodName(
            FileController.class, "serveFile", uuid);
        url = uri.build().toUri().toString();
    }
}
