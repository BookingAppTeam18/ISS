package rest.domain;

import rest.domain.DTO.AccommodationDTO;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Entity
@Table(name="accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private Long ownerId;
    private String name;
    private double longitude;
    private double latitude;
    private double activePrice;
//    @ElementCollection
//    @CollectionTable(name = "price_list_mapping", joinColumns = @JoinColumn(name = "accommodation_id"))
//    @MapKeyTemporal(TemporalType.DATE)
//    @Column(name = "price")
//    private HashMap<Date, double> priceList;
    private int minNumOfGuests;
    private int maxNumOfGuests;
    @ElementCollection
    @CollectionTable(name = "gallery_mapping", joinColumns = @JoinColumn(name = "accommodation_id"))
    @Column(name = "image_url")
    private List<String> gallery;
    @ElementCollection(targetClass = Benefit.class)
    @CollectionTable(name = "benefits_mapping", joinColumns = @JoinColumn(name = "accommodation_id"))
    @Column(name = "benefit")
    @Enumerated(EnumType.STRING)
    private List<Benefit> benefits;
    @Enumerated(EnumType.STRING)
    private AccommodationType accommodetionType;

    public Accommodation(){
        this.id = null;
        this.ownerId = null;
        this.name = "name";
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.activePrice = 0.0;
//        this.priceList = new HashMap<Date, double>();
        this.minNumOfGuests = 0;
        this.maxNumOfGuests = 0;
        this.gallery = new ArrayList<String>();
        this.benefits = new ArrayList<Benefit>();
        this.accommodetionType = null;
    }
    public Accommodation(Long id, Long ownerId, String name, double longitude, double latitude, double activePrice, int minNumOfGuests, int maxNumOfGuests, List<String> gallery, List<Benefit> benefits, AccommodationType accommodetionType) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.activePrice = activePrice;
//        this.priceList = priceList;
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
        this.longitude = accommodationDTO.getLongitude();
        this.latitude = accommodationDTO.getLatitude();
        this.activePrice = accommodationDTO.getActivePrice();
//        this.priceList = new HashMap<>();
        this.minNumOfGuests = accommodationDTO.getMinNumOfGuests();
        this.maxNumOfGuests = accommodationDTO.getMaxNumOfGuests();
        this.gallery = new ArrayList<>();
        this.benefits = new ArrayList<>();
        this.accommodetionType = accommodationDTO.getAccommodationType();
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getActivePrice() {
        return activePrice;
    }

    public void setActivePrice(double activePrice) {
        this.activePrice = activePrice;
    }

//    public HashMap<Date, Double> getPriceList() {
//        return priceList;
//    }
//
//    public void setPriceList(HashMap<Date, Double> priceList) {
//        this.priceList = priceList;
//    }

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
        this.longitude = accommodation.getLongitude();
        this.latitude = accommodation.getLatitude();
        this.activePrice = accommodation.getActivePrice();
//        this.priceList = accommodation.getPriceList();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.gallery = accommodation.getGallery();
        this.benefits = accommodation.getBenefits();
        this.accommodetionType = accommodation.getAccommodetionType();
    }
}
