package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import rest.domain.AccommodationRequest;
import rest.repository.AccommodationRequestRepository;

import java.util.Collection;

public class AccommodationRequestService implements IService<AccommodationRequest> {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Override
    public Collection<AccommodationRequest> findAll() {
        return accommodationRequestRepository.findAll();
    }

    @Override
    public AccommodationRequest findOne(Long id) {
        return accommodationRequestRepository.findOne(id);
    }

    @Override
    public AccommodationRequest create(AccommodationRequest accommodationRequest) throws Exception {
        if (accommodationRequest.getId() != null){
        throw new Exception("Id not null");
        }
        return accommodationRequestRepository.create(accommodationRequest);

    }

    @Override
    public AccommodationRequest update(AccommodationRequest accommodationRequest) throws Exception {
        AccommodationRequest accommodationToUpdate = findOne(accommodationRequest.getId());
        if(accommodationToUpdate == null){
        throw new Exception("Accommodation request not found");
        }
        accommodationToUpdate.copyValues(accommodationRequest);
        return accommodationRequestRepository.create(accommodationToUpdate);
    }

    @Override
    public void delete(Long id) {
        accommodationRequestRepository.delete(id);
    }
}
