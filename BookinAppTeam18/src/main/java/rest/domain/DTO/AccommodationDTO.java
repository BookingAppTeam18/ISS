package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.enumerations.AccommodationState;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;
import java.util.ArrayList;
import java.util.List;

public class AccommodationDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private double longitude;
    private double latitude;
    private String location;
    private int minNumOfGuests;
    private int maxNumOfGuests;
    private List<String> gallery;
    private List<Benefit> benefits;
    private AccommodationType accommodationType;
    private double rating;
    private double nextPrice;

    public double getNextPrice() {
        return nextPrice;
    }

    public void setNextPrice(double nextPrice) {
        this.nextPrice = nextPrice;
    }

    private String description;

    private AccommodationState accommodationState;


    public AccommodationDTO() {

    }

    public AccommodationDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.ownerId = accommodation.getOwner().getId();
        this.name = accommodation.getName();
        this.longitude = accommodation.getLongitude();
        this.latitude = accommodation.getLatitude();
        this.location = accommodation.getLocation();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.gallery = new ArrayList<String>();
        this.gallery = accommodation.getGallery();
        this.benefits = new ArrayList<Benefit>();
        this.benefits = accommodation.getBenefits();
        this.accommodationType = accommodation.getAccommodetionType();
        this.description = accommodation.getDescription();
        this.accommodationState = accommodation.getAccommodationState();
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getLocation() {
        return location;
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

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public String getDescription() {
        return description;
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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setDescription (String description){
        this.description = description;

    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public AccommodationState getAccommodationState() {
        return accommodationState;
    }

    public void setAccommodationState(AccommodationState accommodationState) {
        this.accommodationState = accommodationState;
    }
}
