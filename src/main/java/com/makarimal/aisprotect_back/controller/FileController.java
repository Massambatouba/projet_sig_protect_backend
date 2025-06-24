package com.makarimal.aisprotect_back.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {

    @GetMapping("/uploads/{filename}")
    public FileSystemResource getImage(@PathVariable String filename) {
        Path filePath = Paths.get("uploads").resolve(filename).normalize();
        return new FileSystemResource(filePath);
    }
}