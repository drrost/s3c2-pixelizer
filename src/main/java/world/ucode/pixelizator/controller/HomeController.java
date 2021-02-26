package world.ucode.pixelizator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/homeddd")
    String home() {
        return "You are at Pixelizator home page";
    }
}
