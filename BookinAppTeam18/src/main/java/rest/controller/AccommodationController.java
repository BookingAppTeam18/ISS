package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;
import rest.service.AccommodationService;
import rest.service.FilterService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

    @GetMapping(value="/paging/{start}/{offset}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getNAccommodations(@PathVariable("start") int start, @PathVariable("offset") int offset) {
        Collection<AccommodationDTO> accommodations= accommodationService.findNAccommodations(start,offset);
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
            @RequestParam(name = "benefits", required = false) ArrayList<Benefit> benefits,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "minNumberOfGuests", required = false) Integer minNumberOfGuests,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end
            // Add more filter parameters as needed
    ) throws ParseException {
        filterService.FillFilter();
        if(benefits != null){
            filterService.filterAccommodationsBenefits(benefits);
        }
        if (type != null) {
            filterService.filterAccommodationsType(type);
        }
        if (location != null) {
            filterService.filterAccommodationsLocationName(location);
        }
        if(minNumberOfGuests != null){
            filterService.filterAccommodationsMinNumberOfGuests(minNumberOfGuests);
        }
        if (minPrice != null) {
            filterService.filterAccommodationsMinPrice(minPrice);
        }
        if (maxPrice != null) {
            filterService.filterAccommodationsMaxPrice(maxPrice);
        }
        if (start != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(start);
            filterService.filterAccommodationsStart(startDate);
        }
        if (end != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = dateFormat.parse(end);
            filterService.filterAccommodationsEnd(endDate);
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
