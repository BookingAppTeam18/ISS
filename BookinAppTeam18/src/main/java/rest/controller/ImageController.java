package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rest.service.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") List<MultipartFile> files, @RequestParam("accommodationId") Long accommodationId) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("Select file to upload");
        }
        try {
            imageService.saveImages(files,accommodationId);
            return ResponseEntity.ok("Files successfully uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload files");
        }
    }
}
