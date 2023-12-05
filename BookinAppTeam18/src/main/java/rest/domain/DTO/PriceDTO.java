package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.Price;
import java.util.Date;

public class PriceDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double amount;
    private Long accommodationId;

    public PriceDTO(Price price){
        this.id = price.getId();
        this.startDate = price.getStart();
        this.endDate = price.getEndDate();
        this.amount = price.getAmount();
        this.accommodationId = price.getAccommodation().getId();

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
}
