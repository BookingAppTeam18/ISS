package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.AccommodationComment;

import java.util.Collection;

public interface AccommodationCommentRepository extends JpaRepository<AccommodationComment,Long> {
    @Query("select c from AccommodationComment c where c.accommodation.id= ?1")
    public Collection<AccommodationComment> FindAccommodationComments(Long id);
}
