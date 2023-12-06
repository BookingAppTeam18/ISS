package rest.domain.DTO;

import java.util.ArrayList;
import java.util.List;

public class AccommodationDetailsDTO {
    AccommodationDTO accommodationDTO;
    List<CommentDTO> commentsDTO;

    public AccommodationDetailsDTO(AccommodationDTO accommodationDTO, List<CommentDTO> commentsDTO) {
        this.accommodationDTO = accommodationDTO;
        this.commentsDTO = commentsDTO;
    }

    public AccommodationDetailsDTO() {
        accommodationDTO = null;
        commentsDTO = new ArrayList<>();
    }

    public AccommodationDTO getAccommodationDTO() {
        return accommodationDTO;
    }

    public void setAccommodationDTO(AccommodationDTO accommodationDTO) {
        this.accommodationDTO = accommodationDTO;
    }

    public List<CommentDTO> getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(List<CommentDTO> commentsDTO) {
        this.commentsDTO = commentsDTO;
    }
}
