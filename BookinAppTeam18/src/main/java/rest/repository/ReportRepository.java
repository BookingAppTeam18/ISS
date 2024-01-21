package rest.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.domain.Account;
import rest.domain.Notification;
import rest.domain.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query("select r from Report r where r.reportedCommentId = ?1")
    Report[] findCommentReports(Long accountId);

    @Query("select r from Report r where r.reportedUser = ?1")
    Report[] findAccountReports(Long accountId);

    @Query("select r.reportedUser from Report r where r.reportedCommentId = -1 and r.reportedUser.userState != 1")
    List<Account> findAccountsReports();
}
