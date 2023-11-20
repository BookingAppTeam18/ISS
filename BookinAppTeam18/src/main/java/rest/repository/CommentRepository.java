package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Comment;

import java.util.Collection;

@Repository
public class CommentRepository implements IRepository<Comment> {
    public Collection<Comment> findAll() {
        return null;
    }

    public Comment findOne(Long id) {
        return null;
    }

    @Override
    public Comment update(Comment greeting) {
        return null;
    }

    public Comment create(Comment comment) {
        return null;
    }

    public void delete(Long id) {
    }

    public Collection<Comment> findPageComments(long pageId) {
        return null;
    }
}
