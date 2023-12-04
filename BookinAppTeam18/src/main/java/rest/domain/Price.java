package rest.domain;


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
    private double price;


    public Price() {
    }


    public Price(Date start, Date end, double value) {
        this.start = start;
        this.endDate = end;
        this.price = value;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
