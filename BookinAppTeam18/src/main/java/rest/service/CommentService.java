package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Comment;
import rest.domain.Greeting;
import rest.repository.CommentRepository;
import rest.repository.InMemoryGreetingRepository;

import java.util.Collection;

@Service
public class CommentService implements IService<Comment>{

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Collection<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    @Override
    public Comment create(Comment comment) throws Exception {
        if (comment.getId() != null) {
            throw new Exception("Id can't be null");
        }
        return commentRepository.create(comment);
    }

    @Override
    public Comment update(Comment comment) throws Exception {
        Comment commentToUpdate = findOne(comment.getId());
        if (commentToUpdate == null) {
            throw new Exception("Not Found.");
        }
        commentToUpdate.copyValues(comment);
        return commentToUpdate;
    }

    @Override
    public void delete(Long id) {
        commentRepository.delete(id);
    }

    public Collection<Comment> findAccommodationComments(Long accommodationId) {
        return commentRepository.findAccommodatioComments(accommodationId);
    }
    public Collection<Comment> findAccountComments(Long accountId) {
        return commentRepository.findAccountComments(accountId);
    }
}
