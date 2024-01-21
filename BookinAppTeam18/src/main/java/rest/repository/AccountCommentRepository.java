package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.AccountComment;

import java.util.Collection;

public interface AccountCommentRepository extends JpaRepository<AccountComment,Long> {
    @Query("select c from AccountComment c where c.account.id= ?1 and c.commentState='APPROVED'")
    public Collection<AccountComment>  FindAccountComments(Long id);
}
