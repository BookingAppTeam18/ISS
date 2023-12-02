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
public class AccommodationRequestService implements IService<AccommodationRequestDTO> {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Collection<AccommodationRequestDTO> findAll() {

//        return accommodationRequestRepository.findAll();
        return null;
    }

    @Override
    public AccommodationRequestDTO findOne(Long id) {

//        return accommodationRequestRepository.findOne(id);
        return null;
    }

    @Override
    public AccommodationRequestDTO insert(AccommodationRequestDTO greeting) throws Exception {
        return null;
    }


    @Override
    public AccommodationRequestDTO update(AccommodationRequestDTO accommodationRequestDTO) throws Exception {
        AccommodationRequestDTO accommodationToUpdate = findOne(accommodationRequestDTO.getId());
        if(accommodationToUpdate == null){
        throw new Exception("Accommodation request not found");
        }
//        accommodationToUpdate.copyValues(accommodationRequestDTO);
//        return accommodationRequestRepository.create(accommodationToUpdate);
        return null;
    }

    @Override
    public AccommodationRequestDTO delete(Long id) {
        return null;
    }

    @Override
    public void deleteAll() {

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
