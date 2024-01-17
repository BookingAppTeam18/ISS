package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;
import rest.service.AccommodationService;
import rest.service.FilterService;
import rest.service.ImageService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private FilterService filterService;
    @Autowired
    private ImageService imageService;

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService.findAll();
        getAccommodationImages(accommodations);
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value="/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getPendingAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService.findPendingAccommodations();
        getAccommodationImages(accommodations);
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value="/{start}/{offset}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getNAccommodations(@PathVariable("start") int start,
                                                                           @PathVariable("offset") int offset) {
        Collection<AccommodationDTO> accommodations= accommodationService.findNAccommodations(start,offset);
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value="/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> getAccommodationDetails(@PathVariable("id") Long id) {
        AccommodationDetailsDTO accommodationDetails = accommodationService.findAccommodationDetails(id);
        getAccommodationImages(accommodationDetails.getAccommodationDTO());
        return new ResponseEntity<AccommodationDetailsDTO>(accommodationDetails, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('GUEST','OWNER')")
    @GetMapping(value = "/owner/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsByOwner(@PathVariable("id") Long ownerId) {
        Collection<AccommodationDTO> accommodations = accommodationService.findAccommodationsForOwner(ownerId);
        getAccommodationImages(accommodations);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        AccommodationDTO accommodationDTO = accommodationService.findOne(id);

        if (accommodationDTO == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        getAccommodationImages(accommodationDTO);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value ="/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodationsByFilter(
            @RequestParam(name = "type", required = false) AccommodationType type,
            @RequestParam(name = "benefits", required = false) ArrayList<Benefit> benefits,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "minNumberOfGuests", required = false) Integer minNumberOfGuests,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end,
            @RequestParam(name = "search", required = false) String search
            // Add more filter parameters as needed
    ) throws ParseException {
        filterService.FillFilter();
        if(search != null && !search.isEmpty()){
            filterService.Search(search);
        }
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
        getAccommodationImages(filteredAccommodations);

        return new ResponseEntity<Collection<AccommodationDTO>>(filteredAccommodations, HttpStatus.OK);
    }

    @GetMapping(value ="/{start}/{offset}/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getNAccommodationsByFilter(
            @PathVariable("start") int begin,
            @PathVariable("offset") int offset,
            @RequestParam(name = "type", required = false) AccommodationType type,
            @RequestParam(name = "benefits", required = false) ArrayList<Benefit> benefits,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "minNumberOfGuests", required = false) Integer minNumberOfGuests,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end,
            @RequestParam(name = "search", required = false) String search
            // Add more filter parameters as needed
    ) throws ParseException {
        filterService.FillFilter();
        if(search != null && !search.isEmpty()){
            filterService.Search(search);
        }
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

        ArrayList<AccommodationDTO> filteredAccommodations = new ArrayList<>(filterService.toDTO());
        Collection<AccommodationDTO> cutFilteredAccommodations;


        if (begin >= 0 && begin < filteredAccommodations.size() && offset > 0) {
            int endIndex = Math.min(begin + offset, filteredAccommodations.size());
            cutFilteredAccommodations = filteredAccommodations.subList(begin, endIndex);
        } else{
            cutFilteredAccommodations =  Collections.emptyList();
        }

        getAccommodationImages(cutFilteredAccommodations);

        return new ResponseEntity<Collection<AccommodationDTO>>(cutFilteredAccommodations, HttpStatus.OK);
    }

    private void getAccommodationImages(Collection<AccommodationDTO> accommodations) {
        for(AccommodationDTO accommodationDTO:accommodations){
            accommodationDTO.setGallery(imageService.getAccommodationImages(accommodationDTO.getId()));
        }
    }

    private void getAccommodationImages(AccommodationDTO accommodation) {
        accommodation.setGallery(imageService.getAccommodationImages(accommodation.getId()));
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody AccommodationDTO accommodationDto) throws Exception {
        AccommodationDTO savedAccommodation = accommodationService.insert(accommodationDto);
        return new ResponseEntity<AccommodationDTO>(savedAccommodation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodation, @PathVariable Long id)
            throws Exception {
        accommodation.setId(id);
        AccommodationDTO updatedAccommodation = accommodationService.update(accommodation);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Accommodation> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<Accommodation>(HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping(value = "/approve/{id}/{option}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> approveAccommodation(@PathVariable("id") Long id, @PathVariable("option") int option) throws Exception {
        AccommodationDTO accommodationDTO = accommodationService.approveAccommodation(id, option);
        return new ResponseEntity<AccommodationDTO>(accommodationDTO, HttpStatus.OK);
    }

}
