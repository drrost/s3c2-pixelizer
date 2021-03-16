package world.ucode.pixelizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import world.ucode.pixelizator.controller.model.TLFile;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.services.DownloadFilesService;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.storage.exceptions.FileStoreFileNotFoundException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private final FileService fileService;
    private final Pixelator pixelator;
    private final DownloadFilesService downloadFilesService;

    @Autowired
    public FileController(FileService fileService, Pixelator pixelator, DownloadFilesService downloadFilesService) {
        this.fileService = fileService;
        this.pixelator = pixelator;
        this.downloadFilesService= downloadFilesService;
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws FileDaoException {

        var files = fileService.all().stream().map(
            (file) -> {
                return new TLFile(file);
            }).collect(Collectors.toList());

        model.addAttribute("files", files);

        return "files";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws FileDaoException {

        var file = fileService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getName() + "\"").body(file.getResource());
    }

    @PostMapping("/")
    public String handleManyFilesUpload(
        @RequestParam("files") List<MultipartFile> files,
        @RequestParam("pixel_size_input") int pixelSize,
        RedirectAttributes redirectAttributes) throws FileDaoException, IOException {

        return pixelizeFiles(pixelSize, redirectAttributes, files);
    }

    @PostMapping("/files/byurl")
    public String handleFileByUrlUpload(
        @RequestParam("url") String url,
        @RequestParam("pixel_size_input") int pixelSize,
        RedirectAttributes redirectAttributes) throws IOException, FileDaoException {

        var multipartFile = downloadFilesService.download(url);
        var files = Arrays.asList(multipartFile);
        downloadFilesService.deleteFile(multipartFile.getName());

        return pixelizeFiles(pixelSize, redirectAttributes, files);
    }

    private String pixelizeFiles(
        @RequestParam("pixel_size_input") int pixelSize,
        RedirectAttributes redirectAttributes,
        List<MultipartFile> files) throws FileDaoException, IOException {

        var pixelizedFiles = pixelator.handleFiles(files, pixelSize);
        var pixelizedTlFiles = pixelizedFiles.stream().map(file -> {
            return new TLFile(file);
        }).collect(Collectors.toList());

        redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + files.size() + " files");

        redirectAttributes.addFlashAttribute("pixelized_files", pixelizedTlFiles);

        return "redirect:/";
    }

    @ExceptionHandler(FileStoreFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileStoreFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
