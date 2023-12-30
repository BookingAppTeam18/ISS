package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.service.PriceService;

import java.util.Collection;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceDTO>> getPrices() {
        Collection<PriceDTO> comments = priceService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER')")
    @GetMapping(value = "/accommodation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceDTO>> getAccommodationPrices(@PathVariable("id") Long accommodationId) {
        Collection<PriceDTO> prices = priceService.findPricesForAccommodation(accommodationId);
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> getPrice(@PathVariable("id") Long id) {
        PriceDTO price = priceService.findOne(id);

        if (price == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO price) throws Exception {
        PriceDTO savedPrice = priceService.insert(price);
        return new ResponseEntity<>(savedPrice, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> updatePrice(@RequestBody PriceDTO price, @PathVariable Long id)
            throws Exception {
        price.setId(id);
        PriceDTO updatedPrice = priceService.update(price);

        if (updatedPrice == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> updateOrCreatePrice(@RequestBody PriceDTO price, @PathVariable Long id)
            throws Exception {
        price.setId(id);
        PriceDTO updatedPrice = priceService.updateOrCreate(price);

        if (updatedPrice == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Price> deletePrice(@PathVariable("id") Long id) {
        priceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}