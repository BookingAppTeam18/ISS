package rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Accommodation;
import rest.service.AccommodationService;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodations() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<Accommodation>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Accommodation>(accommodation, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        Accommodation savedAccommodation = accommodationService.create(accommodation);
        return new ResponseEntity<Accommodation>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationForUpdate = accommodationService.findOne(id);
        accommodationForUpdate.copyValues(accommodation);

        Accommodation updatedAccommodation = accommodationService.update(accommodationForUpdate);

        if (updatedAccommodation == null) {
            return new ResponseEntity<Accommodation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Accommodation>(updatedAccommodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Accommodation> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<Accommodation>(HttpStatus.NO_CONTENT);
    }

}
