package rest.service;

import org.springframework.stereotype.Service;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.ReservationDTO;

import java.util.Collection;

@Service
public class ReservationService implements IService<ReservationDTO> {

    @Override
    public Collection<ReservationDTO> findAll() {
        return null;
    }

    @Override
    public ReservationDTO findOne(Long id) {
        return null;
    }

    public Collection<AccommodationDTO> findUserAccommodations(Long userId) {
        return null;
    }

    public Collection<AccountDTO> findAccommodationGuests(Long accommodationId){
            return null;
    }

    @Override
    public ReservationDTO insert(ReservationDTO greeting) throws Exception {
        return null;
    }

    @Override
    public ReservationDTO update(ReservationDTO greeting) throws Exception {
        return null;
    }

    @Override
    public ReservationDTO delete(Long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
