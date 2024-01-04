package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rest.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/image")
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

    @GetMapping("/{accommodationId}")
    public ResponseEntity<Collection<String>> getAccommodationImages(@PathVariable("accommodationId") Long accommodationId) {
        ArrayList<String> images = imageService.getAccommodationImages(accommodationId);
        if(images == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
