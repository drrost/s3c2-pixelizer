package world.ucode.pixelizator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.storage.FileStore;
import world.ucode.pixelizator.storage.exceptions.FileStoreFileNotFoundException;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileController {

    private final FileStore fileStore;
    private final FileService fileService;

    @Autowired
    public FileController(FileStore fileStore, FileService fileService) {
        this.fileStore = fileStore;
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException, FileDaoException {

        model.addAttribute("files", fileService.all().stream().map(
            file -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                "serveFile", file.getName().toString()).build().toUri().toString())
            .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileStore.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws FileDaoException {

        fileService.add(file);
        redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(FileStoreFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileStoreFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
