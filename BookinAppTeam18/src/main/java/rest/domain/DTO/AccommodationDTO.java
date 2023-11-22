package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import java.util.ArrayList;
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
    private AccommodationType accommodetionType;

    public AccommodationDTO(){

    }

    public AccommodationDTO(Accommodation accommodation){
        this.id = accommodation.getId();
        this.ownerId = accommodation.getOwnerId();
        this.name = accommodation.getName();
        this.location = accommodation.getLocation();
        this.activePrice = accommodation.getActivePrice();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.gallery = new ArrayList<String>();
        this.benefits = new ArrayList<Benefit>();
        this.accommodetionType = accommodation.getAccommodetionType();
    }

    public AccommodationDTO(Long id, Long ownerId, String name, String location, double activePrice, int minNumOfGuests, int maxNumOfGuests, List<String> gallery, List<Benefit> benefits, AccommodationType accommodetionType) {
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

    public AccommodationType getAccommodetionType() {
        return accommodetionType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActivePrice(double activePrice) {
        this.activePrice = activePrice;
    }

    public void setMinNumOfGuests(int minNumOfGuests) {
        this.minNumOfGuests = minNumOfGuests;
    }

    public void setMaxNumOfGuests(int maxNumOfGuests) {
        this.maxNumOfGuests = maxNumOfGuests;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public void setAccommodetionType(AccommodationType accommodetionType) {
        this.accommodetionType = accommodetionType;
    }
}
