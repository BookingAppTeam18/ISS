package rest.domain;

import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.Page;

import javax.persistence.*;

@Entity
@Table(name="AccommodationComments")
public class AccommodationComment extends Comment{
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "accommodationId")
    private Accommodation accommodation;
    public AccommodationComment( ) {
    }


    public AccommodationComment(CommentDTO commentDTO) {
        super(commentDTO);
    }


    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
