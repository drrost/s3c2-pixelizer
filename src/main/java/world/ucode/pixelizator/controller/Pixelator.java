package world.ucode.pixelizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.services.model.FSFileModel;
import world.ucode.pixelizator.util.FilenamesHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.List;

@Component
public class Pixelator {

    private FileService fileService;

    @Autowired
    public Pixelator(FileService fileService) {
        this.fileService = fileService;
    }

    public void handleFiles(List<MultipartFile> fileList) throws FileDaoException, IOException {
        // For each file
        //   save file
        //   pixelate file
        //   save pixelated file
        //   save link

        for (MultipartFile file : fileList) {
            var model = FSFileModel.create(file);
            var infile = fileService.add(model);
            pixelate(file);
        }
    }

    private void pixelate(MultipartFile file) {
        try {
            final int PIX_SIZE = 10;
            BufferedImage image = ImageIO.read(file.getInputStream());
            // Get the raster data (array of pixels)
            Raster src = image.getData();

            // Create an identically-sized output raster
            WritableRaster dest = src.createCompatibleWritableRaster();

            // Loop through every PIX_SIZE pixels, in both x and y directions
            for (int y = 0; y < src.getHeight(); y += PIX_SIZE) {
                for (int x = 0; x < src.getWidth(); x += PIX_SIZE) {

                    // Copy the pixel
                    double[] pixel = new double[3];
                    pixel = src.getPixel(x, y, pixel);

                    // "Paste" the pixel onto the surrounding PIX_SIZE by PIX_SIZE neighbors
                    // Also make sure that our loop never goes outside the bounds of the image
                    for (int yd = y; (yd < y + PIX_SIZE) && (yd < dest.getHeight()); yd++) {
                        for (int xd = x; (xd < x + PIX_SIZE) && (xd < dest.getWidth()); xd++) {
                            dest.setPixel(xd, yd, pixel);
                        }
                    }
                }
            }

            // Save the raster back to the Image
            image.setData(dest);

            var name = FilenamesHelper.addSuffixToFileName(file.getOriginalFilename(), "_pixelized");
            var model = FSFileModel.create(image, name);

            // Write the new file
            fileService.add(model);
        } catch (IOException | FileDaoException e) {
            e.printStackTrace();
        }
    }
}
