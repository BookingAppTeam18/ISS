package rest.domain.DTO;

import java.util.ArrayList;
import java.util.List;

public class AccountDetailsDTO {
    public rest.domain.DTO.AccountDTO getAccountDTO() {
        return AccountDTO;
    }

    public void setAccountDTO(rest.domain.DTO.AccountDTO accountDTO) {
        AccountDTO = accountDTO;
    }

    public List<CommentDTO> getCommentsDTO() {
        return commentsDTO;
    }

    public void setCommentsDTO(List<CommentDTO> commentsDTO) {
        this.commentsDTO = commentsDTO;
    }

    AccountDTO AccountDTO;
    List<CommentDTO> commentsDTO;

    public AccountDetailsDTO(AccountDTO accountDTO, List<CommentDTO> commentsDTO) {
        this.AccountDTO = accountDTO;
        this.commentsDTO = commentsDTO;
    }

    public AccountDetailsDTO() {
        AccountDTO = null;
        commentsDTO = new ArrayList<>();
    }
}
