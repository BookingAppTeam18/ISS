package rest.domain;

import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id",length = 5)
    private Long id;
    @NotNull
    @Future(message = "Start date must be in the future")
    private Date startDate;
    @NotNull
    @Future(message = "End date must be in the future")
    private Date endDate;
    private double price;
    private Long accountId;
    private Long accommodationId;
    private int numberOfGuests;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;


    public Reservation(){

    }

    public Reservation(Long id, Date startDate, Date endDate, double price, Long accountId, Long accommodationId,int numberOfGuests , ReservationStatus reservationStatus) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.accountId = accountId;
        this.accommodationId = accommodationId;
        this.reservationStatus = reservationStatus;
        this.numberOfGuests = numberOfGuests;
    }

    public Reservation(ReservationDTO reservationDTO){
        this(reservationDTO.getId(), reservationDTO.getStartDate(), reservationDTO.getEndDate(), reservationDTO.getPrice(), reservationDTO.getAccountId(), reservationDTO.getAccommodationId(),reservationDTO.getNumberOfGuests(), ReservationStatus.CREATED);
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

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void copyValues(Reservation reservation){
        this.startDate = reservation.startDate;
        this.endDate = reservation.endDate;
        this.price = reservation.price;
        this.reservationStatus = reservation.reservationStatus;
        this.accountId = reservation.accountId;
        this.accommodationId = reservation.accommodationId;
        this.numberOfGuests = reservation.numberOfGuests;
    }
}
