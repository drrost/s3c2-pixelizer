package world.ucode.pixelizator.services.model;

import org.springframework.core.io.Resource;

public class FSFileModel {

    private Resource resource;
    private String name;

    public FSFileModel(Resource resource, String name) {
        this.resource = resource;
        this.name = name;
    }

    public Resource getResource() {
        return resource;
    }

    public String getName() {
        return name;
    }
}
