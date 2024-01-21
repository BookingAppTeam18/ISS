package rest.domain;

import rest.domain.DTO.AccommodationReportDTO;

import javax.persistence.*;

@Entity
@Table(name="accommodation_reports")
public class AccommodationReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;
    private int numberOfReservations;
    private double income;
    private Long ownerId;

    public AccommodationReport(){

    }

    public AccommodationReport(Long id, Accommodation accommodation, int numberOfReservations, double income, Long ownerId) {
        this.id = id;
        this.accommodation = accommodation;
        this.numberOfReservations = numberOfReservations;
        this.income = income;
        this.ownerId = ownerId;
    }

    public AccommodationReport(AccommodationReportDTO accommodationReportDTO) {
        this.id = accommodationReportDTO.getAccommodationId();
        this.numberOfReservations = accommodationReportDTO.getNumberOfReservations();
        this.income = accommodationReportDTO.getIncome();
        this.ownerId = accommodationReportDTO.getOwnerId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}


