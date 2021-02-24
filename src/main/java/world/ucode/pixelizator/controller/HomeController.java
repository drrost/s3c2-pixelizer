package world.ucode.pixelizator.controller;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

@RestController
public class HomeController {

    @GetMapping("/")
    String home() {
        return "You are at Pixelizator home page";
    }
}
