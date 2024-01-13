package rest.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rest.domain.Accommodation;
import rest.repository.AccommodationRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    AccommodationRepository accommodationRepository;

    public void saveImages(List<MultipartFile> files, Long accommodationId) throws IOException {
        Accommodation accommodation = accommodationRepository.getOne(accommodationId);
        List<String> fileUrls = new ArrayList<>();

        String relativePath = "BookinAppTeam18/src/main/resources/static/";

        // Koristimo Paths za kreiranje apsolutne putanje
        Path absolutePath = Paths.get(System.getProperty("user.dir"), relativePath);

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String baseFileName = fileName.substring(0, fileName.lastIndexOf('.'));
            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));

            // Prilagodite rezoluciju slike za malu sliku
            int smallWidth = 200;
            int smallHeight = 200;
            File smallFile = resizeImage(file, baseFileName + "_small" + fileExtension, absolutePath, smallWidth, smallHeight);
            accommodation.getGallery().add(smallFile.getAbsolutePath());

            // Prilagodite rezoluciju slike za veliku sliku
            int bigWidth = 400;
            int bigHeight = 400;
            File bigFile = resizeImage(file, baseFileName + "_big" + fileExtension, absolutePath, bigWidth, bigHeight);
            accommodation.getGallery().add(bigFile.getAbsolutePath());

        }
        accommodationRepository.save(accommodation);
        accommodationRepository.flush();
    }

    private File resizeImage(MultipartFile file, String fileName, Path absolutePath, int width, int height) throws IOException {
        // Ne koristite tempFile u ovom trenutku, već koristite samo stream
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = absolutePath.resolve(fileName);

            // Prilagodite rezoluciju slike
            Thumbnails.of(inputStream)
                    .size(width, height)
                    .toFile(filePath.toFile());

            // Nema potrebe za brisanjem tempFile-a, jer ga nismo ni kreirali

            return filePath.toFile();
        }
    }

    public ArrayList<String> getAccommodationImages(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.getOne(accommodationId);
        String begin = "http://localhost:8080/api/content/";

        ArrayList<String> images = new ArrayList<>(accommodation.getGallery());

        ArrayList<String> paths = new ArrayList<>();

        for (String path : images) {
            Path filePath = Paths.get(path);
            String fileName = filePath.getFileName().toString();
            String fullPath = begin + fileName;
            paths.add(fullPath);
        }

        return paths;
    }
}