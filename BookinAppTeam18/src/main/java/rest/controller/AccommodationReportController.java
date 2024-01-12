package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.AccommodationReport;
import rest.domain.DTO.AccommodationReportDTO;
import rest.service.AccommodationReportService;

import java.util.Collection;

@RestController
@RequestMapping("api/accommodationReports")
public class AccommodationReportController {
    @Autowired
    private AccommodationReportService accommodationReportService;

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationReportDTO>> getAccommodations() {
        Collection<AccommodationReportDTO> accommodations = accommodationReportService.findAll();
        return new ResponseEntity<Collection<AccommodationReportDTO>>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReportDTO> getAccommodation(@PathVariable("id") Long id) {
        AccommodationReportDTO accommodationDTO = accommodationReportService.findOne(id);

        if (accommodationDTO == null) {
            return new ResponseEntity<AccommodationReportDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccommodationReportDTO>(accommodationDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReportDTO> createAccommodationReport(
            @RequestBody AccommodationReportDTO accommodationDto) throws Exception {

        AccommodationReportDTO savedAccommodation = accommodationReportService.insert(accommodationDto);
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReportDTO> updateAccommodation(@RequestBody AccommodationReportDTO accommodation, @PathVariable Long id)
            throws Exception {
        accommodation.setId(id);
        AccommodationReportDTO updatedAccommodation = accommodationReportService.update(accommodation);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationReportDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationReportDTO>(updatedAccommodation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationReport> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationReportService.delete(id);
        return new ResponseEntity<AccommodationReport>(HttpStatus.NO_CONTENT);
    }
}
