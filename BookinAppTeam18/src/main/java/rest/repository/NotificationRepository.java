package rest.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rest.domain.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    @Query("select n from Notification n where n.account.id = ?1 and n.seen=false ")
    Notification[] findUnseenNotifications(Long accountId);

    @Query("select n from Notification n where n.account.id = ?1")
    Notification[] findAccountNotifications(Long accountId);

}
