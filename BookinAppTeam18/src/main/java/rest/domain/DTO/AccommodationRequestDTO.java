package rest.domain.DTO;

import rest.domain.enumerations.AccommodationState;

import java.text.ParseException;

//@Data
public class AccommodationRequestDTO {
    private Long id;
    private Long accommodationId;
    private AccommodationState accommodationState;

    //Treba seter za enum mozda
}
