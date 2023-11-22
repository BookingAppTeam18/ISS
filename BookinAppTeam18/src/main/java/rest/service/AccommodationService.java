package rest.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Accommodation;
import rest.domain.DTO.AccommodationDTO;
import rest.repository.AccommodationRepository;

import java.util.Collection;

@Service
public class AccommodationService implements IService<Accommodation> {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Collection<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Accommodation findOne(Long id) {
        return accommodationRepository.findOne(id);
    }

    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        if (accommodation.getId() != null){
            throw new Exception("Id not null");
        }
        return accommodationRepository.create(accommodation);

    }

    @Override
    public Accommodation update(Accommodation accommodation) throws Exception {
        Accommodation accommodationToUpdate = findOne(accommodation.getId());
        if(accommodationToUpdate == null){
            throw new Exception("Accommodation not found");
        }
        accommodationToUpdate.copyValues(accommodation);
        return accommodationRepository.create(accommodationToUpdate);
    }

    @Override
    public void delete(Long id) {
        accommodationRepository.delete(id);
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
