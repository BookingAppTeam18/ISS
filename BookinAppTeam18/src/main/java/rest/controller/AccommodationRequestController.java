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
import rest.service.AccommodationRequestService;

@RestController
@RequestMapping("/api/accommodationRequests")
public class AccommodationRequestController {

    @Autowired
    private AccommodationRequestService accommodationRequestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationRequest>> getAccommodationRequests() {
        Collection<AccommodationRequest> accommodationRequests = accommodationRequestService.findAll();
        return new ResponseEntity<Collection<AccommodationRequest>>(accommodationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequest> getAccommodationRequest(@PathVariable("id") Long id) {
        AccommodationRequest accommodationRequest = accommodationRequestService.findOne(id);

        if (accommodationRequest == null) {
            return new ResponseEntity<AccommodationRequest>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccommodationRequest>(accommodationRequest, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequest> createAccommodationRequest(@RequestBody AccommodationRequest accommodationRequest) throws Exception {
        AccommodationRequest savedAccommodationRequest = accommodationRequestService.create(accommodationRequest);
        return new ResponseEntity<AccommodationRequest>(savedAccommodationRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationRequest> updateAccommodationRequest(@RequestBody AccommodationRequest accommodationRequest, @PathVariable Long id)
            throws Exception {
        AccommodationRequest accommodationRequestForUpdate = accommodationRequestService.findOne(id);
        accommodationRequestForUpdate.copyValues(accommodationRequest);

        AccommodationRequest updatedAccommodationRequest = accommodationRequestService.update(accommodationRequestForUpdate);

        if (updatedAccommodationRequest == null) {
            return new ResponseEntity<AccommodationRequest>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationRequest>(updatedAccommodationRequest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationRequest> deleteAccommodationRequest(@PathVariable("id") Long id) {
        accommodationRequestService.delete(id);
        return new ResponseEntity<AccommodationRequest>(HttpStatus.NO_CONTENT);
    }

}
