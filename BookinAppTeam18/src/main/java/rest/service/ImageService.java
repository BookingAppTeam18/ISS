package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rest.domain.Accommodation;
import rest.repository.AccommodationRepository;

import java.io.IOException;
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
            Path filePath = absolutePath.resolve(fileName);
            accommodation.getGallery().add(filePath.toString());
            System.out.println(filePath);


            // Sačuvajte fajl na željenu putanju
            file.transferTo(filePath.toFile());

            // Dodajte URL fajla u listu
            fileUrls.add(filePath.toString());



            accommodationRepository.save(accommodation);
            accommodationRepository.flush();
        }


    }

    public ArrayList<String> getAccommodationImages(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.getOne(accommodationId);
        return new ArrayList<>(accommodation.getGallery());
    }
}
