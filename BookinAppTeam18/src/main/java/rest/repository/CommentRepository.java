package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Comment;
import rest.domain.enumerations.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Repository
public class CommentRepository implements IRepository<Comment> {
    Collection<Comment> comments = new ArrayList<>();
    public Collection<Comment> findAll() {

        comments.add(new Comment(2L, "accommodation 1 poruka", 5, 13, 5, Page.ACCOMMODATION));
        comments.add(new Comment(3L, "accommodation 2 poruka", 5, 13, 10, Page.ACCOMMODATION));
        comments.add(new Comment(4L, "account 1 poruka", 5, 13, 5, Page.ACCOUNT));
        comments.add(new Comment(5L, "account 2 poruka", 5, 13, 10, Page.ACCOUNT));

        return comments;
    }

    public Comment findOne(Long id) {
        return findOneComment(id);
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

    private Comment findOneComment(Long id) {
        for(Comment comment:comments){
            if(Objects.equals(comment.getId(), id))
                return comment;
        }
        return null;
    }

    public Collection<Comment> findAccommodatioComments(Long accommodationId) {
        return AccommodationComments(accommodationId);
    }

    private Collection<Comment> AccommodationComments(Long accommodationId) {
        ArrayList<Comment> accommodationComments = new ArrayList<Comment>();
        for(Comment comment:comments){
            if(comment.getPage() == Page.ACCOMMODATION && comment.getWrittenTo() == accommodationId)
                accommodationComments.add(comment);
        }
        return accommodationComments;
    }

    public Collection<Comment> findAccountComments(Long accountId) {
        ArrayList<Comment> accountComments = new ArrayList<Comment>();
        for(Comment comment:comments){
            if(comment.getPage() == Page.ACCOUNT && comment.getWrittenTo() == accountId)
                accountComments.add(comment);
        }
        return accountComments;
    }
}
