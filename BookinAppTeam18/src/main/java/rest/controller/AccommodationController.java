package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.enumerations.AccommodationType;
import rest.service.AccommodationService;
import rest.service.FilterService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private FilterService filterService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService.findAll();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value="/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> getAccommodationDetails(@PathVariable("id") Long id) {
        AccommodationDetailsDTO accommodationDetails = accommodationService.findAccommodationDetails(id);
        return new ResponseEntity<AccommodationDetailsDTO>(accommodationDetails, HttpStatus.OK);
    }
  
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        AccommodationDTO accommodationDTO = accommodationService.findOne(id);

        if (accommodationDTO == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.OK);
    }

    @GetMapping(value ="/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationByFilter(
            @RequestParam(name = "type", required = false) AccommodationType type,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "minPrice", required = false) double minPrice,
            @RequestParam(name = "maxPrice", required = false) double maxPrice
            // Add more filter parameters as needed
    ) {
        filterService.FillFilter();
        if (type != null) {
            // Pozivamo filter samo po tipu
            filterService.filterAccommodationsType(type);
        }if (location != null) {
            // Pozivamo filter samo po tipu
            filterService.filterAccommodationsLocationName(location);
        }if (minPrice != 0) {
            // Pozivamo filter samo po tipu
            filterService.filterAccommodationsMinPrice(minPrice);
        }if (maxPrice != 0) {
            // Pozivamo filter samo po tipu
            filterService.filterAccommodationsMaxPrice(maxPrice);
        }
        Collection<AccommodationDTO> filteredAccommodations = filterService.toDTO();
        return new ResponseEntity<Collection<AccommodationDTO>>(filteredAccommodations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody AccommodationDTO accommodationDto) throws Exception {
        AccommodationDTO savedAccommodation = accommodationService.insert(accommodationDto);
        return new ResponseEntity<AccommodationDTO>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodation, @PathVariable Long id)
            throws Exception {
        AccommodationDTO accommodationForUpdate = accommodationService.findOne(id);
//        accommodationForUpdate.copyValues(accommodation);

        AccommodationDTO updatedAccommodation = accommodationService.update(accommodationForUpdate);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Accommodation> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<Accommodation>(HttpStatus.NO_CONTENT);
    }

}
