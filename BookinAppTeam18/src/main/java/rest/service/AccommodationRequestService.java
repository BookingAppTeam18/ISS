package rest.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.AccommodationRequest;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationRequestDTO;
import rest.repository.AccommodationRequestRepository;

import java.util.Collection;
@Service
public class AccommodationRequestService implements IService<AccommodationRequest> {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    private AccommodationRequestDTO convertEntityToDto(AccommodationRequest accommodationRequest){
        AccommodationRequestDTO accommodationRequestDTO = new AccommodationRequestDTO();
        accommodationRequestDTO = modelMapper.map(accommodationRequest, AccommodationRequestDTO.class);
        return accommodationRequestDTO;
    }

    private AccommodationRequest convertDtoToEntity(AccommodationRequestDTO accommodationRequestDTO){
        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest = modelMapper.map(accommodationRequestDTO, AccommodationRequest.class);
        return accommodationRequest;
    }
}
