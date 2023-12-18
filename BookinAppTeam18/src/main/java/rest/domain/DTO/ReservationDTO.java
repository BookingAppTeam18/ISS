package rest.domain.DTO;

import rest.domain.Reservation;

import java.util.Date;

public class ReservationDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double price;
    private Long accountId;
    private Long accommodationId;
    private int numberOfGuests;

    public ReservationDTO(Reservation reservation){
        this(reservation.getId(), reservation.getStartDate(), reservation.getEndDate(), reservation.getPrice(), reservation.getAccountId(), reservation.getAccommodationId(), reservation.getNumberOfGuests());
    }
    public ReservationDTO(){

    }
    public ReservationDTO(Long id, Date startDate, Date endDate, double price, Long accountId, Long accommodationId, int numberOfGuests) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.accountId = accountId;
        this.accommodationId = accommodationId;
        this.numberOfGuests = numberOfGuests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
