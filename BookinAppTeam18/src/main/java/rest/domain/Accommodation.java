package rest.domain;

import rest.domain.DTO.AccommodationDTO;
import rest.domain.enumerations.AccommodationState;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private Account owner;
    private String name;
    private double longitude;
    private double latitude;
    private String location;
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
    private String description;
    @Enumerated(EnumType.STRING)
    private AccommodationState accommodationState;
    private boolean isAutomaticallyReserved;
    private int reservationDeadline;
    public Accommodation(Long id, Account owner, String name, double longitude, double latitude, double activePrice, int minNumOfGuests, int maxNumOfGuests, ArrayList<String> gallery, List<Benefit> benefits, AccommodationType accommodetionType, String description, AccommodationState accommodationState, boolean isAutomaticallyReserved, int reservationDeadline) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.minNumOfGuests = minNumOfGuests;
        this.maxNumOfGuests = maxNumOfGuests;
        this.gallery = gallery;
        this.benefits = benefits;
        this.accommodetionType = accommodetionType;
        this.description = description;
        this.accommodationState = accommodationState;
        this.isAutomaticallyReserved = isAutomaticallyReserved;
        this.reservationDeadline = reservationDeadline;
    }

    public Accommodation(AccommodationDTO accommodationDTO) {
        this.id = accommodationDTO.getId();
        this.name = accommodationDTO.getName();
        this.longitude = accommodationDTO.getLongitude();
        this.latitude = accommodationDTO.getLatitude();
        this.location = accommodationDTO.getLocation();
        this.minNumOfGuests = accommodationDTO.getMinNumOfGuests();
        this.maxNumOfGuests = accommodationDTO.getMaxNumOfGuests();
        this.gallery = new ArrayList<>();
        this.benefits = accommodationDTO.getBenefits();
        this.accommodetionType = accommodationDTO.getAccommodationType();
        this.description = accommodationDTO.getDescription();
        this.accommodationState = accommodationDTO.getAccommodationState();
        this.isAutomaticallyReserved = accommodationDTO.isAutomaticallyReserved();
        this.reservationDeadline = accommodationDTO.getReservationDeadline();
    }

    public Accommodation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccommodationState getAccommodationState() {
        return accommodationState;
    }

    public void setAccommodationState(AccommodationState accommodationState) {
        this.accommodationState = accommodationState;
    }
    public boolean isAutomaticallyReserved() {
        return isAutomaticallyReserved;
    }
    public void setAutomaticallyReserved(boolean automaticallyReserved) {
        isAutomaticallyReserved = automaticallyReserved;
    }
    public int getReservationDeadline() {
        return reservationDeadline;
    }
    public void setReservationDeadline(int reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }
    public void copyValues(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.owner = accommodation.getOwner();
        this.name = accommodation.getName();
        this.longitude = accommodation.getLongitude();
        this.latitude = accommodation.getLatitude();
        this.minNumOfGuests = accommodation.getMinNumOfGuests();
        this.maxNumOfGuests = accommodation.getMaxNumOfGuests();
        this.gallery = accommodation.getGallery();
        this.benefits = accommodation.getBenefits();
        this.accommodetionType = accommodation.getAccommodetionType();
    }
}
