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

import rest.domain.AccommodationRequest;
import rest.domain.DTO.AccommodationRequestDTO;
import rest.service.AccommodationRequestService;

@RestController
@RequestMapping("/api/accommodationRequests")
public class AccommodationRequestController {

    @Autowired
    private AccommodationRequestService accommodationRequestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationRequestDTO>> getAccommodationRequests() {
        Collection<AccommodationRequestDTO> accommodationRequests = accommodationRequestService.findAll();
        return new ResponseEntity<Collection<AccommodationRequestDTO>>(accommodationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequestDTO> getAccommodationRequest(@PathVariable("id") Long id) {
        AccommodationRequestDTO accommodationRequest = accommodationRequestService.findOne(id);

        if (accommodationRequest == null) {
            return new ResponseEntity<AccommodationRequestDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccommodationRequestDTO>(accommodationRequest, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequestDTO> createAccommodationRequest(@RequestBody AccommodationRequestDTO accommodationRequest) throws Exception {
        AccommodationRequestDTO savedAccommodationRequest = accommodationRequestService.create(accommodationRequest);
        return new ResponseEntity<AccommodationRequestDTO>(savedAccommodationRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequestDTO> updateAccommodationRequest(@RequestBody AccommodationRequestDTO accommodationRequest, @PathVariable Long id)
            throws Exception {
        accommodationRequest.setId(id);
        AccommodationRequestDTO updatedAccommodationRequest = accommodationRequestService.update(accommodationRequest);

        if (updatedAccommodationRequest == null) {
            return new ResponseEntity<AccommodationRequestDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationRequestDTO>(updatedAccommodationRequest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationRequestDTO> deleteAccommodationRequest(@PathVariable("id") Long id) {
        accommodationRequestService.delete(id);
        return new ResponseEntity<AccommodationRequestDTO>(HttpStatus.NO_CONTENT);
    }

}
