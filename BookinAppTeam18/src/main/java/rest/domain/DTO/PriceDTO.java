package rest.domain.DTO;

import rest.domain.Price;

import java.util.Date;

public class PriceDTO {
    private Long id;
    private long startDate;
    private long endDate;
    private double amount;
    private Long accommodationId;
    private boolean isPerNight;

    public PriceDTO(Price price){
        this.id = price.getId();
        this.startDate = price.getStart().getTime();
        this.endDate = price.getEndDate().getTime();
        this.amount = price.getAmount();
        this.accommodationId = price.getAccommodation().getId();
        this.isPerNight = price.isPerNight();
    }

    public PriceDTO(){
        
    }

    public PriceDTO(Long id, long startDate, long endDate, double amount, Long accommodationId, boolean isPerNight) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.accommodationId = accommodationId;
        this.isPerNight = isPerNight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return new Date(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate.getTime();
    }

    public Date getEndDate() {
        return new Date(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate.getTime();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public boolean isPerNight() {
        return isPerNight;
    }

    public void setPerNight(boolean perNight) {
        isPerNight = perNight;
    }
}
