package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.repository.CommentRepository;
import rest.repository.NotificationRepository;

import java.util.Collection;

@Service
public class NotificationService implements IService<NotificationDTO> {

    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Collection<NotificationDTO> findAll() {
        return null;
    }

    @Override
    public NotificationDTO findOne(Long id) {
        return null;
    }

    @Override
    public NotificationDTO insert(NotificationDTO object) throws Exception {
        return null;
    }

    @Override
    public NotificationDTO update(NotificationDTO object) throws Exception {
        return null;
    }

    @Override
    public NotificationDTO delete(Long id) {
        return null;
    }


    @Override
    public void deleteAll() {

    }

    public Collection<NotificationDTO> findAccountUnseenNotifications(Long accountId) {
        return null;
    }

    public NotificationDTO setSeen(Long notificationId) {
        return null;
    }

    public Collection<NotificationDTO> findAccountNotifications(Long accountId) {
        return null;
    }
}
