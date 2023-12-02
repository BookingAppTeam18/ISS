package rest.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.enumerations.AccommodationType;
import rest.repository.AccommodationRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccommodationService implements IService<AccommodationDTO> {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Collection<AccommodationDTO> findAll() {
        ArrayList<AccommodationDTO> accommodationDTOS = new ArrayList<AccommodationDTO>();
        for (Accommodation a : accommodationRepository.findAll()){
            accommodationDTOS.add(new AccommodationDTO(a));
        }
        return accommodationDTOS;
    }

    @Override
    public AccommodationDTO findOne(Long id) {
        return new AccommodationDTO(accommodationRepository.findOne(id));
    }

    @Override
    public AccommodationDTO insert(AccommodationDTO greeting) throws Exception {
        return null;
    }


    @Override
    public AccommodationDTO update(AccommodationDTO accommodationDTO) throws Exception {
        Accommodation accommodationToUpdate = accommodationRepository.findOne(accommodationDTO.getId());
        if(accommodationToUpdate == null){
            throw new Exception("Accommodation not found");
        }
        accommodationToUpdate.copyValues(new Accommodation(accommodationDTO));
        return new AccommodationDTO(accommodationToUpdate);
    }

    @Override
    public AccommodationDTO delete(Long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }


    public Collection<AccommodationDTO> findByFilter(AccommodationType accommodationType){
        return null;
    }

    private AccommodationDTO convertEntityToDto(Accommodation accommodation){
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        accommodationDTO = modelMapper.map(accommodation, AccommodationDTO.class);
        return accommodationDTO;
    }

    private Accommodation convertDtoToEntity(AccommodationDTO accommodationDTO){
        Accommodation accommodation = new Accommodation();
        accommodation = modelMapper.map(accommodationDTO, Accommodation.class);
        return accommodation;
    }
}
