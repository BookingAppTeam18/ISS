package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Notification;
import rest.repository.CommentRepository;
import rest.repository.NotificationRepository;

import java.util.Collection;

@Service
public class NotificationService implements IService<Notification> {

    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Collection<Notification> findAll() {
        return null;
    }

    @Override
    public Notification findOne(Long id) {
        return null;
    }

    @Override
    public Notification create(Notification object) throws Exception {
        return null;
    }

    @Override
    public Notification update(Notification object) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
