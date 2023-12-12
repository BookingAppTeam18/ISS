package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationType;
import rest.repository.AccommodationRepository;
import rest.repository.PriceRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class FilterService {

    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private PriceRepository priceRepository;
    public Collection<Accommodation> accommodations;
    public FilterService(){
    }

    public void FillFilter(){
        accommodations = accommodationRepository.findAll();
    }
    //filter by type
    public Collection<Accommodation> filterAccommodationsType(AccommodationType type){
        for(Accommodation a :accommodations){
            if(a.getAccommodetionType()!=type)
                accommodations.remove(a);
        }
        return accommodations;
    }


    //filter by location name
    public Collection<Accommodation> filterAccommodationsLocationName(String location) {
        for(Accommodation a :accommodations){
            if(!a.getLocation().equals(location))
                accommodations.remove(a);
        }
          return accommodations;
    }

    //filter accommodation if it has price at any time higher then min price
    public Collection<Accommodation> filterAccommodationsMinPrice(double minPrice) {
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if( !findMoreThenMin(prices,minPrice)){
                accommodations.remove(a);
            }
        }
        return accommodations;
    }

    private boolean findMoreThenMin(Collection<Price> prices,double minPrice) {
        for(Price price:prices){
            if(price.getAmount() > minPrice && price.getEndDate().after(new Date())){
                return true;
            }
        }
        return false;
    }

    //filter accommodation if it has price at any time lower then max price
    public Collection<Accommodation> filterAccommodationsMaxPrice(double maxPrice) {
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if(!findLessThenMax(prices,maxPrice)){
                accommodations.remove(a);
            }
        }
        return accommodations;
    }

    private boolean findLessThenMax(Collection<Price> prices, double maxPrice) {
        for(Price price:prices){
            if(price.getAmount() < maxPrice && price.getEndDate().after(new Date())){
                return true;
            }
        }
        return false;
    }

    public Collection<AccommodationDTO> toDTO() {
        Collection<AccommodationDTO> accommodationsDTO = new ArrayList<>();
        for(Accommodation accommodation:accommodations){
            accommodationsDTO.add(new AccommodationDTO((accommodation)));
        }
        return accommodationsDTO;
    }
}
