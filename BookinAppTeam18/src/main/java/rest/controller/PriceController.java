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

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceDTO>> getPrices() {
        Collection<PriceDTO> comments = priceService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO price) throws Exception {
        PriceDTO savedPrice = priceService.insert(price);
        return new ResponseEntity<>(savedPrice, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PostMapping(value = "/addMultiple",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceDTO>> createPrices(@RequestBody Collection<PriceDTO> prices) throws Exception {
        Collection<PriceDTO> savedPrices = new ArrayList<>();

        for (PriceDTO price:prices) {
            PriceDTO savedPrice = priceService.insert(price);
            savedPrices.add(savedPrice);
        }
        return new ResponseEntity<>(savedPrices, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> updatePrice(@RequestBody PriceDTO price)
            throws Exception {
        PriceDTO updatedPrice = priceService.insert(price);

        if (updatedPrice == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @PutMapping(value = "/accommodation/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceDTO>> updatePrices(@RequestBody Collection<PriceDTO> prices,@PathVariable("id") Long accommodationId)
            throws Exception {
        Collection<PriceDTO> updatedPrices = priceService.updatePrices(prices,accommodationId);

        if (updatedPrices == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedPrices, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('OWNER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Price> deletePrice(@PathVariable("id") Long id) {
        priceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}