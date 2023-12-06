package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.repository.AccommodationRepository;
import rest.repository.PriceRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
@Service
public class PriceService implements IService<PriceDTO> {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;
    @Override
    public Collection<PriceDTO> findAll() {
        ArrayList<PriceDTO> priceDTOS = new ArrayList<>();
        for(Price price:priceRepository.findAll()){
            priceDTOS.add(new PriceDTO(price));
        }
        return priceDTOS;
    }

    @Override
    public PriceDTO findOne(Long id)
    {
        Optional<Price> found = priceRepository.findById(id);
        return new PriceDTO(found.get());
    }

    @Override
    public PriceDTO insert(PriceDTO priceDTO){
        Price price = new Price(priceDTO);
        price.setAccommodation(accommodationRepository.getOne(priceDTO.getAccommodationId()));
        try {
            Price savedPrice = priceRepository.save(price);
            priceRepository.flush();
            return new PriceDTO(savedPrice);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }

    @Override
    public PriceDTO update(PriceDTO priceDTO) throws Exception {
        Price priceToUpdate = new Price(priceDTO);
        try {
            findOne(priceDTO.getId()); // this will throw ResponseStatusException if student is not found
            priceRepository.save(priceToUpdate);
            priceRepository.flush();
            return priceDTO;
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public PriceDTO delete(Long id) {
        Price found = new Price(findOne(id)); // this will throw StudentNotFoundException if student is not found
        priceRepository.delete(found);
        priceRepository.flush();
        return new PriceDTO(found);
    }

    @Override
    public void deleteAll() {
        priceRepository.deleteAll();
        priceRepository.flush();
    }

    public Collection<PriceDTO> findPricesForAccommodation(Long accommodationId) {
        ArrayList<PriceDTO>  accommodationPrices = new ArrayList<>();
        for(Price price : priceRepository.findPricesForAccommodation(accommodationId)){
            accommodationPrices.add(new PriceDTO(price));
        }
        return accommodationPrices;
    }
}