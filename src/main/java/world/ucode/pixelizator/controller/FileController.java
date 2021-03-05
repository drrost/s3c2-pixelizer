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
import world.ucode.pixelizator.controller.model.TLFile;
import world.ucode.pixelizator.dao.error.FileDaoException;
import world.ucode.pixelizator.services.FileService;
import world.ucode.pixelizator.storage.FileStore;
import world.ucode.pixelizator.storage.exceptions.FileStoreFileNotFoundException;

import java.io.IOException;
import java.util.List;
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

        var files = fileService.all().stream().map(
            (file) -> {
                var uuid = String.valueOf(file.getId());
                var uri = MvcUriComponentsBuilder.fromMethodName(
                    FileController.class, "serveFile", uuid);

                var tlFile = new TLFile(file);
                tlFile.url = uri.build().toUri().toString();
                return tlFile;
            }).collect(Collectors.toList());

        model.addAttribute("files", files);

        return "uploadForm";
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
        RedirectAttributes redirectAttributes) throws FileDaoException {

        for (MultipartFile file : files)
            fileService.add(file);

        redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded " + files.size() + " files");
        return "redirect:/";
    }

    @ExceptionHandler(FileStoreFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileStoreFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
