package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.enumerations.AccommodetionType;
import rest.domain.enumerations.Benefit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//@Data
public class AccommodationDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private String location;
    private double activePrice;
    private int minNumOfGuests;
    private int maxNumOfGuests;
    private List<String> gallery;
    private List<Benefit> benefits;
    private AccommodetionType accommodetionType;

    public AccommodationDTO(){

    }

    public AccommodationDTO(Accommodation accommodation){
        this.gallery = new ArrayList<String>();
        this.benefits = new ArrayList<Benefit>();
//
    }

    public AccommodationDTO(Long id, Long ownerId, String name, String location, double activePrice, int minNumOfGuests, int maxNumOfGuests, List<String> gallery, List<Benefit> benefits, AccommodetionType accommodetionType) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
        this.activePrice = activePrice;
        this.minNumOfGuests = minNumOfGuests;
        this.maxNumOfGuests = maxNumOfGuests;
        this.gallery = gallery;
        this.benefits = benefits;
        this.accommodetionType = accommodetionType;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getActivePrice() {
        return activePrice;
    }

    public int getMinNumOfGuests() {
        return minNumOfGuests;
    }

    public int getMaxNumOfGuests() {
        return maxNumOfGuests;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public AccommodetionType getAccommodetionType() {
        return accommodetionType;
    }
}
