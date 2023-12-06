package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.enumerations.AccommodationType;
import rest.repository.AccommodationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FilterService {

    @Autowired
    private AccommodationRepository accommodationRepository;
    public static Collection<Accommodation> accommodations;
    public FilterService(){
        this.accommodations = accommodationRepository.findAll();
    }
    public Collection<AccommodationDTO> filterAccommodationsType(AccommodationType type){
        ArrayList<AccommodationDTO> accommodationType= new ArrayList<>();
        for(Accommodation a :accommodationRepository.findAccommodationType(type)){
            accommodationType.add(new AccommodationDTO(a));
        }
        return accommodationType;
    }

    public Collection<AccommodationDTO> filterAccommodationsLocation(double longitude, double latitude){
        ArrayList<AccommodationDTO>  accommodationType= new ArrayList<>();
        for(Accommodation a :accommodationRepository.findAccommodationLocation(longitude, latitude)){
            accommodationType.add(new AccommodationDTO(a));
        }
        return accommodationType;
    }

    public Collection<AccommodationDTO> filterAccommodationsLocationName(String location) {
    }

    public Collection<AccommodationDTO> filterAccommodationsMinPrice(double minPrice) {
    }

    public Collection<AccommodationDTO> filterAccommodationsMaxPrice(double maxPrice) {
    }
}
