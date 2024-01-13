package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.*;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.AccommodationState;
import rest.domain.enumerations.CommentState;
import rest.domain.enumerations.Page;
import rest.repository.AccommodationCommentRepository;
import rest.repository.AccommodationRepository;
import rest.repository.AccountCommentRepository;
import rest.repository.AccountRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class CommentService implements IService<CommentDTO>{

    @Autowired
    private AccountCommentRepository accountCommentRepository;
    @Autowired
    private AccommodationCommentRepository accommodationCommentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;
   @Override
    public Collection<CommentDTO> findAll() {

        ArrayList<CommentDTO> commentsDTO = new ArrayList<>();
        for(AccommodationComment accommodationComment:accommodationCommentRepository.findAll()){
            commentsDTO.add(new CommentDTO(accommodationComment));
        }
        for(AccountComment accountComment:accountCommentRepository.findAll()){
            commentsDTO.add(new CommentDTO(accountComment));
        }
        return commentsDTO;
    }

    @Override
    public CommentDTO findOne(Long id)
    {
        Optional<AccommodationComment> found = accommodationCommentRepository.findById(id);
        if(found.isEmpty()){
            Optional<AccountComment> found2 = accountCommentRepository.findById(id);
            if (found2.isEmpty()) {
                String value = "notFound";
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
            }
            return new CommentDTO(found2.get());
        }
        return new CommentDTO(found.get());
    }

    @Override
    public CommentDTO insert(CommentDTO commentDTO){
        try {
            if(commentDTO.getPage()== Page.ACCOMMODATION){
                Accommodation accommodation = accommodationRepository.getOne(commentDTO.getWrittenToId());
                Account writtenBy = accountRepository.getOne(commentDTO.getWrittenById());

                AccommodationComment accommodationComment = new AccommodationComment(commentDTO);
                accommodationComment.setAccommodation(accommodation);
                accommodationComment.setWrittenBy(writtenBy);

                AccommodationComment savedComment = accommodationCommentRepository.save(accommodationComment);
                accommodationRepository.flush();
                return new CommentDTO(savedComment);
            }
            Account account = accountRepository.getOne(commentDTO.getWrittenToId());
            Account writtenBy = accountRepository.getOne(commentDTO.getWrittenById());

            AccountComment accountComment = new AccountComment(commentDTO);
            accountComment.setAccount(account);
            accountComment.setWrittenBy(writtenBy);

            AccountComment savedComment = accountCommentRepository.save(accountComment);
            accountCommentRepository.flush();
            return new CommentDTO(savedComment);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) throws Exception {
        try {
            if(commentDTO.getPage()== Page.ACCOMMODATION){
                Accommodation accommodation = accommodationRepository.getOne(commentDTO.getWrittenToId());
                Account writtenBy = accountRepository.getOne(commentDTO.getWrittenById());

                AccommodationComment accommodationCommentToUpdate = new AccommodationComment(commentDTO);
                accommodationCommentToUpdate.setAccommodation(accommodation);
                accommodationCommentToUpdate.setWrittenBy(writtenBy);

                findOne(commentDTO.getId());
                AccommodationComment updatedComment = accommodationCommentRepository.save(accommodationCommentToUpdate);
                accommodationRepository.flush();
                return new CommentDTO(updatedComment);
            }
            Account account = accountRepository.getOne(commentDTO.getWrittenToId());
            Account writtenBy = accountRepository.getOne(commentDTO.getWrittenById());

            AccountComment accountCommentToUpdate = new AccountComment(commentDTO);
            accountCommentToUpdate.setAccount(account);
            accountCommentToUpdate.setWrittenBy(writtenBy);

            findOne(commentDTO.getId());
            AccountComment updatedComment = accountCommentRepository.save(accountCommentToUpdate);
            accountCommentRepository.flush();
            return new CommentDTO(updatedComment);
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public CommentDTO delete(Long id) {


        CommentDTO found = findOne(id);
        if(found.getPage()==Page.ACCOUNT){
            Account account = accountRepository.getOne(found.getWrittenToId());

            AccountComment commentToDelete = new AccountComment(found);
            commentToDelete.setAccount(account);

            accountCommentRepository.delete(commentToDelete);
            accountCommentRepository.flush();
            return found;
        }
        Accommodation accommodation = accommodationRepository.getOne(found.getWrittenToId());

        AccommodationComment commentToDelete = new AccommodationComment(found);
        commentToDelete.setAccommodation(accommodation);

        accommodationCommentRepository.delete(commentToDelete);
        accommodationCommentRepository.flush();
        return found;

    }

    @Override
    public void deleteAll() {
        accountCommentRepository.deleteAll();
        accountCommentRepository.flush();
        accommodationCommentRepository.deleteAll();
        accommodationCommentRepository.flush();
    }

    public Collection<CommentDTO> findAccommodationComments(Long accommodationId) {
        ArrayList<CommentDTO>  accommodationComments= new ArrayList<>();
        for(AccommodationComment accommodationComment:accommodationCommentRepository.FindAccommodationComments(accommodationId)){
            accommodationComments.add(new CommentDTO(accommodationComment));
        }
        return accommodationComments;
    }
    public Collection<CommentDTO> findAccountComments(Long accountId) {
        ArrayList<CommentDTO>  accountComments= new ArrayList<>();
        for(AccountComment accountComment:accountCommentRepository.FindAccountComments(accountId)){
            accountComments.add(new CommentDTO(accountComment));
        }
        return accountComments;
    }
    public CommentDTO approveComment(Long accommodationId, int option) {
        Comment comment = accommodationCommentRepository.getOne(accommodationId);
        if(option == 0)
            comment.setCommentState(CommentState.DENIED);
        else
            comment.setCommentState(CommentState.APPROVED);
        if(comment.getPage().equals(Page.ACCOUNT)) {
            accountCommentRepository.save((AccountComment) comment);
            accountCommentRepository.flush();
            return new CommentDTO((AccountComment) comment);
        }
        else{
            accommodationCommentRepository.save((AccommodationComment) comment);
            accommodationCommentRepository.flush();
            return new CommentDTO((AccommodationComment) comment);
        }
    }
}
