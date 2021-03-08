package world.ucode.pixelizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.model.File;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.services.model.FSFileModel;
import world.ucode.pixelizator.util.FilenamesHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Pixelator {

    private FileService fileService;

    @Autowired
    public Pixelator(FileService fileService) {
        this.fileService = fileService;
    }

    public List<File> handleFiles(List<MultipartFile> fileList, int pixelSize) throws FileDaoException, IOException {
        var pixelizedFiles = new ArrayList<File>();
        for (MultipartFile file : fileList) {
            var model = FSFileModel.create(file);
            fileService.add(model);
            var pixelizedFile = pixelate(file, pixelSize);
            pixelizedFiles.add(pixelizedFile);
        }
        return pixelizedFiles;
    }

    private File pixelate(MultipartFile file, int pixelSize) throws IOException, FileDaoException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        Raster src = image.getData();

        WritableRaster dest = src.createCompatibleWritableRaster();

        for (int y = 0; y < src.getHeight(); y += pixelSize) {
            for (int x = 0; x < src.getWidth(); x += pixelSize) {

                double[] pixel = new double[3];
                pixel = src.getPixel(x, y, pixel);

                for (int yd = y; (yd < y + pixelSize) && (yd < dest.getHeight()); yd++) {
                    for (int xd = x; (xd < x + pixelSize) && (xd < dest.getWidth()); xd++) {
                        dest.setPixel(xd, yd, pixel);
                    }
                }
            }
        }

        image.setData(dest);

        var name = FilenamesHelper.addSuffixToFileName(file.getOriginalFilename(), "_pixelized");
        var model = FSFileModel.create(image, name);

        return fileService.add(model);
    }
}
