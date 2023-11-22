package rest.domain;

import rest.domain.DTO.AccommodationDTO;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Accommodation {
    private Long id;
    private Long ownerId;
    private String name;
    private String location;
    private double activePrice;
    private HashMap<Date, Double> priceList;
    private int minNumOfGuests;
    private int maxNumOfGuests;
    private List<String> gallery;
    private List<Benefit> benefits;
    private AccommodationType accommodetionType;

    public Accommodation(){
        this.id = null;
        this.ownerId = null;
        this.name = "name";
        this.location = "location";
        this.activePrice = 0.0;
        this.priceList = new HashMap<Date, Double>();
        this.minNumOfGuests = 0;
        this.maxNumOfGuests = 0;
        this.gallery = new ArrayList<String>();
        this.benefits = new ArrayList<Benefit>();
        this.accommodetionType = null;
    }
    public Accommodation(Long id, Long ownerId, String name, String location, double activePrice, HashMap<Date, Double> priceList, int minNumOfGuests, int maxNumOfGuests, List<String> gallery, List<Benefit> benefits, AccommodationType accommodetionType) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.location = location;
        this.activePrice = activePrice;
        this.priceList = priceList;
        this.minNumOfGuests = minNumOfGuests;
        this.maxNumOfGuests = maxNumOfGuests;
        this.gallery = gallery;
        this.benefits = benefits;
        this.accommodetionType = accommodetionType;
    }

    public Accommodation(AccommodationDTO accommodationDTO) {
        this.id = accommodationDTO.getId();
        this.ownerId = accommodationDTO.getOwnerId();
        this.name = accommodationDTO.getName();
        this.location = accommodationDTO.getLocation();
        this.activePrice = accommodationDTO.getActivePrice();
        this.priceList = new HashMap<>();
        this.minNumOfGuests = accommodationDTO.getMinNumOfGuests();
        this.maxNumOfGuests = accommodationDTO.getMaxNumOfGuests();
        this.gallery = new ArrayList<>();
        this.benefits = new ArrayList<>();
        this.accommodetionType = accommodationDTO.getAccommodetionType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getActivePrice() {
        return activePrice;
    }

    public void setActivePrice(double activePrice) {
        this.activePrice = activePrice;
    }

    public HashMap<Date, Double> getPriceList() {
        return priceList;
    }

    public void setPriceList(HashMap<Date, Double> priceList) {
        this.priceList = priceList;
    }

    public int getMinNumOfGuests() {
        return minNumOfGuests;
    }

    public void setMinNumOfGuests(int minNumOfGuests) {
        this.minNumOfGuests = minNumOfGuests;
    }

    public int getMaxNumOfGuests() {
        return maxNumOfGuests;
    }

    public void setMaxNumOfGuests(int maxNumOfGuests) {
        this.maxNumOfGuests = maxNumOfGuests;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public AccommodationType getAccommodetionType() {
        return accommodetionType;
    }

    public void setAccommodetionType(AccommodationType accommodetionType) {
        this.accommodetionType = accommodetionType;
    }

    public void copyValues(Accommodation accommodation) {

        this.id = accommodation.getId();
        this.ownerId = accommodation.getOwnerId();
        this.name = accommodation.getName();
        this.location = accommodation.getLocation();
        this.activePrice = accommodation.getActivePrice();
        this.priceList = accommodation.getPriceList();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.gallery = accommodation.getGallery();
        this.benefits = accommodation.getBenefits();
        this.accommodetionType = accommodation.getAccommodetionType();
    }
}
