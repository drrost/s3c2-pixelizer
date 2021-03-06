package world.ucode.pixelizator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import world.ucode.pixelizator.dao.error.FileDaoException;

import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException, FileDaoException {
        return "home";
    }
}
