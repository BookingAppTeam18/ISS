package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Comment;
import rest.domain.DTO.CommentDTO;
import rest.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommentService implements IService<CommentDTO>{

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Collection<CommentDTO> findAll() {
        ArrayList<CommentDTO> comments = new ArrayList<>();
        for(Comment comment:commentRepository.findAll()){
            comments.add(new CommentDTO(comment));
        }
        return comments;
    }

    @Override
    public CommentDTO findOne(Long id) {
        return new CommentDTO(commentRepository.findOne(id));
    }

    @Override
    public CommentDTO create(CommentDTO commentDTO) throws Exception {
        if (commentDTO.getId() != null) {
            throw new Exception("Id can't be null");
        }
        return  new CommentDTO(commentRepository.create(new Comment(commentDTO)));
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) throws Exception {
        Comment commentToUpdate = commentRepository.findOne(commentDTO.getId());
        if (commentToUpdate == null) {
            throw new Exception("Not Found.");
        }
        commentToUpdate.copyValues(new Comment(commentDTO));
        return new CommentDTO(commentToUpdate);
    }

    @Override
    public void delete(Long id) {
        commentRepository.delete(id);
    }

    public Collection<CommentDTO> findAccommodationComments(Long accommodationId) {
        ArrayList<CommentDTO>  accommodationComments= new ArrayList<>();
        for(Comment accommodationComment:commentRepository.findAccommodatioComments(accommodationId)){
            accommodationComments.add(new CommentDTO(accommodationComment));
        }
        return accommodationComments;
    }
    public Collection<CommentDTO> findAccountComments(Long accountId) {
        ArrayList<CommentDTO>  accountComments= new ArrayList<>();
        for(Comment accountComment:commentRepository.findAccountComments(accountId)){
            accountComments.add(new CommentDTO(accountComment));
        }
        return accountComments;
    }
}
