package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Comment;
import rest.domain.DTO.CommentDTO;
import rest.domain.Notification;
import rest.domain.enumerations.Page;
import rest.repository.CommentRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class CommentService implements IService<CommentDTO>{

    @Autowired
    private CommentRepository commentRepository;
//    ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
    @Override
    public Collection<CommentDTO> findAll() {

        ArrayList<CommentDTO> commentsDTO = new ArrayList<>();
        for(Comment comment:commentRepository.findAll()){
            commentsDTO.add(new CommentDTO(comment));
        }
        return commentsDTO;
    }

    @Override
    public CommentDTO findOne(Long id)
    {
        Optional<Comment> found = commentRepository.findById(id);
//        if (found.isEmpty()) {
//            String value = bundle.getString("notFound");
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
//        }
        return new CommentDTO(found.get());
    }

    @Override
    public CommentDTO insert(CommentDTO commentDTO){
        Comment comment = new Comment(commentDTO);
        try {
            Comment savedComment = commentRepository.save(comment);
            commentRepository.flush();
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
        Comment commentToUpdate = new Comment(commentDTO);
        try {
            findOne(commentDTO.getId()); // this will throw ResponseStatusException if student is not found
            commentRepository.save(commentToUpdate);
            commentRepository.flush();
            return commentDTO;
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
        Comment found = new Comment(findOne(id)); // this will throw StudentNotFoundException if student is not found
        commentRepository.delete(found);
        commentRepository.flush();
        return new CommentDTO(found);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
        commentRepository.flush();
    }

    public Collection<CommentDTO> findAccommodationComments(Long accommodationId) {
        ArrayList<CommentDTO>  accommodationComments= new ArrayList<>();
        for(Comment accommodationComment:commentRepository.findAccommodationComments(Page.ACCOMMODATION,accommodationId)){
            accommodationComments.add(new CommentDTO(accommodationComment));
        }
        return accommodationComments;
    }
    public Collection<CommentDTO> findAccountComments(Long accountId) {
        ArrayList<CommentDTO>  accountComments= new ArrayList<>();
        for(Comment accountComment:commentRepository.findAccountComments(Page.ACCOUNT,accountId)){
            accountComments.add(new CommentDTO(accountComment));
        }
        return accountComments;
    }
}
