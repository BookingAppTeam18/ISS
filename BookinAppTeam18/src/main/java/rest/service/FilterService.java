package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;
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
    public void filterAccommodationsType(AccommodationType type){
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            if(a.getAccommodetionType()!=type)
                badAccommodations.add(a);
        }
        accommodations.removeAll(badAccommodations);
    }

    //filter by benefits
    public void filterAccommodationsBenefits(Collection<Benefit> benefits) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            Collection<Benefit> accommodationBenefits = a.getBenefits();
            if(!accommodationBenefits.containsAll(benefits)){
                badAccommodations.add(a);
            }
        }
        accommodations.removeAll(badAccommodations);
    }

    //filter by location name
    public void filterAccommodationsLocationName(String location) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            if(!a.getLocation().equals(location))
                badAccommodations.add(a);
        }
        accommodations.removeAll(badAccommodations);
    }

    //filter accommodation if it has price at any time higher then min price
    public void filterAccommodationsMinPrice(Double minPrice) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if( !findMoreThenMin(prices,minPrice)){
                badAccommodations.add(a);
            }
        }
        accommodations.removeAll(badAccommodations);
    }

    private boolean findMoreThenMin(Collection<Price> prices,double minPrice) {
        for(Price price:prices){
            if(price.getAmount() >= minPrice && price.getEndDate().after(new Date())){
                return true;
            }
        }
        return false;
    }

    //filter accommodation if it has price at any time lower then max price
    public void filterAccommodationsMaxPrice(Double maxPrice) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if(!findLessThenMax(prices,maxPrice)){
                badAccommodations.add(a);
            }
        }
        accommodations.removeAll(badAccommodations);
    }

    private boolean findLessThenMax(Collection<Price> prices, double maxPrice) {
        for(Price price:prices){
            if(price.getAmount() <= maxPrice && price.getEndDate().after(new Date())){
                return true;
            }
        }
        return false;
    }


    public void filterAccommodationsMinNumberOfGuests(Integer minNumberOfGuests) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            if(a.getMinNumOfGuests() > minNumberOfGuests)
                badAccommodations.add(a);
        }
        accommodations.removeAll(badAccommodations);
    }

    public void filterAccommodationsStart(Date start) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if(!findInRange(prices,start)){
                badAccommodations.add(a);
            }
        }
        accommodations.removeAll(badAccommodations);
    }

    public void filterAccommodationsEnd(Date end) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            Collection<Price> prices = priceRepository.findPricesForAccommodation(a.getId());
            if(!findInRange(prices,end)){
                badAccommodations.add(a);
            }
        }
        accommodations.removeAll(badAccommodations);
    }

    private boolean findInRange(Collection<Price> prices, Date date) {
        for(Price price:prices){
            if(price.getStart().before(date) && price.getEndDate().after(date)){
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

    public void Search(String search) {
        Collection<Accommodation> badAccommodations = new ArrayList<>();
        for(Accommodation a :accommodations){
            if(!a.getName().toLowerCase().contains(search.toLowerCase()))
                badAccommodations.add(a);
        }
        accommodations.removeAll(badAccommodations);
    }
}
