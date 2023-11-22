package rest.domain.DTO;

import rest.domain.AccommodationRequest;
import rest.domain.enumerations.AccommodationState;

public class AccommodationRequestDTO {
    private Long id;
    private Long accommodationId;
    private AccommodationState accommodationState;

    public AccommodationRequestDTO(){

    }

    public AccommodationRequestDTO(Long id, Long accommodationId, AccommodationState accommodationState) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.accommodationState = accommodationState;
    }

    public AccommodationRequestDTO(AccommodationRequest accommodationRequest){
        this(accommodationRequest.getId(), accommodationRequest.getAccommodationId(), accommodationRequest.getAccommodationState());
    }

    public Long getId() {
        return id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public AccommodationState getAccommodationState() {
        return accommodationState;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public void setAccommodationState(AccommodationState accommodationState) {
        this.accommodationState = accommodationState;
    }
}
