package rest.domain.DTO;

import rest.domain.AccommodationReport;

public class AccommodationReportDTO {
    private Long id;
    private Long accommodationId;
    private int numberOfReservations;
    private int income;
    private Long ownerId;

    public AccommodationReportDTO(AccommodationReport accommodationReport) {
        this.id = accommodationReport.getId();
        this.accommodationId = accommodationReport.getAccommodation().getId();
        this.numberOfReservations = accommodationReport.getNumberOfReservations();
        this.income = accommodationReport.getIncome();
        this.ownerId = accommodationReport.getOwnerId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
