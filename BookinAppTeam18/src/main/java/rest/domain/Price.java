package rest.domain;


import rest.domain.DTO.PriceDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private Date start;
    private Date endDate;
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accommodation_id")
    private Accommodation accommodation;

    public Price() {
    }

    public Price(Date start, Date end, double value) {
        this.id = null;
        this.start = start;
        this.endDate = end;
        this.amount = value;
    }

    public Price(PriceDTO priceDTO){
        this.id = priceDTO.getId();
        this.start = priceDTO.getStartDate();
        this.endDate = priceDTO.getEndDate();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double price) {
        this.amount = price;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price )) return false;
        return id != null && id.equals(((Price) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
