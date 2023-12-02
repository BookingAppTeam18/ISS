package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Comment;
import rest.domain.enumerations.Page;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.page= ?1 and c.writtenTo=?2")
    public Collection<Comment> findAccommodationComments(Page page,Long accommodationId);
    @Query("select c from Comment c where  c.page= ?1 and c.writtenTo=?2")
    public Collection<Comment> findAccountComments(Page page,Long accommodationId);
}
