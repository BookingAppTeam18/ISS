package rest.domain.DTO;

import rest.domain.Accommodation;
import rest.domain.Price;
import java.util.Date;

public class PriceDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double amount;

    private Accommodation accommodation;

    public PriceDTO(Price price, Accommodation accommodation){
        this.id = price.getId();
        this.startDate = price.getStart();
        this.endDate = price.getEndDate();
        this.amount = price.getAmount();
        this.accommodation = accommodation;

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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
