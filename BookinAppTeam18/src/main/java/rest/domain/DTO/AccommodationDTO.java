package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import java.util.ArrayList;
import java.util.List;
//@Data
public class AccommodationDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private double longitude;
    private double latitude;
    private int minNumOfGuests;
    private int maxNumOfGuests;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    private List<Price>prices;
    private List<String> gallery;
    private List<Benefit> benefits;
    private AccommodationType accommodationType;

    public AccommodationDTO(){

    }

    public AccommodationDTO(Accommodation accommodation){
        this.id = accommodation.getId();
        this.ownerId = accommodation.getOwnerId();
        this.name = accommodation.getName();
        this.longitude = accommodation.getLongitude();
        this.latitude = accommodation.getLatitude();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.prices = accommodation.getPrices();
        this.gallery = new ArrayList<String>();
        this.gallery = accommodation.getGallery();
        this.benefits = new ArrayList<Benefit>();
        this.benefits = accommodation.getBenefits();
        this.accommodationType = accommodation.getAccommodetionType();
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
}
