package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Notification;

import java.util.Collection;

@Repository
public class NotificationRepository implements IRepository<Notification> {
    @Override
    public Collection<Notification> findAll() {
        return null;
    }

    @Override
    public Notification create(Notification object) {
        return null;
    }

    @Override
    public Notification findOne(Long id) {
        return null;
    }

    @Override
    public Notification update(Notification object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
