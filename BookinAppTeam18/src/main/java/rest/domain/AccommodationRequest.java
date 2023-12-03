package rest.domain;

import rest.domain.DTO.AccommodationRequestDTO;
import rest.domain.enumerations.AccommodationState;

import javax.persistence.*;

@Entity
@Table(name="accommodationRequests")
public class AccommodationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private Long accommodationId;
    @Enumerated(EnumType.STRING)
    private AccommodationState accommodationState;

    public AccommodationRequest(){
        this.id = null;
        this.accommodationId = null;
        this.accommodationState = null;
    }
    public AccommodationRequest(Long id, Long accommodationId, AccommodationState accommodationState) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.accommodationState = accommodationState;
    }

    public AccommodationRequest(AccommodationRequestDTO ardto) {
        this.id = ardto.getId();
        this.accommodationId = ardto.getAccommodationId();
        this.accommodationState = ardto.getAccommodationState();
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

    public AccommodationState getAccommodationState() {
        return accommodationState;
    }

    public void setAccommodationState(AccommodationState accommodationState) {
        this.accommodationState = accommodationState;
    }

    public void copyValues(AccommodationRequest accommodationRequest) {
        this.id = accommodationRequest.getId();
        this.accommodationId = accommodationRequest.getAccommodationId();
        this.accommodationState = accommodationRequest.getAccommodationState();
    }
}
