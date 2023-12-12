package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.repository.AccountRepository;
import rest.repository.NotificationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class NotificationService implements IService<NotificationDTO> {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountRepository accountRepository;
 @Override
    public Collection<NotificationDTO> findAll() {

        ArrayList<NotificationDTO> notificationsDTO = new ArrayList<>();
        for(Notification notification:notificationRepository.findAll()){
            notificationsDTO.add(new NotificationDTO(notification));
        }
        return notificationsDTO;
    }

    @Override
    public NotificationDTO findOne(Long id)
    {
        Optional<Notification> found = notificationRepository.findById(id);
        if (found.isEmpty()) {
            String value = "Not Found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
        }
        return new NotificationDTO(found.get());
    }

    @Override
    public NotificationDTO insert(NotificationDTO NotificationDTO){
        Notification notification = new Notification(NotificationDTO);
        notification.setAccount(accountRepository.getOne(NotificationDTO.getAccountId()));
        try {
            Notification savedNotification = notificationRepository.save(notification);
            notificationRepository.flush();
            return new NotificationDTO(savedNotification);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }

    @Override
    public NotificationDTO update(NotificationDTO NotificationDTO) throws Exception {
        Notification notificationToUpdate = new Notification(NotificationDTO);
        notificationToUpdate.setAccount(accountRepository.getOne(NotificationDTO.getAccountId()));
        try {
            findOne(NotificationDTO.getId()); // this will throw ResponseStatusException if student is not found
            Notification updatedNotification =notificationRepository.save(notificationToUpdate);
            notificationRepository.flush();
            return new NotificationDTO(updatedNotification);
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public NotificationDTO delete(Long id) {
        NotificationDTO found = findOne(id); // this will throw StudentNotFoundException if student is not found
        notificationRepository.delete(new Notification(found));
        notificationRepository.flush();
        return found;
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
        notificationRepository.flush();
    }

    public Collection<NotificationDTO> findAccountUnseenNotifications(Long accountId) {
        ArrayList<NotificationDTO>  unseenNotitications= new ArrayList<>();
        for(Notification unseenNotification:notificationRepository.findUnseenNotifications(accountId)){
            unseenNotitications.add(new NotificationDTO(unseenNotification));
        }
        return unseenNotitications;
    }

    public Collection<NotificationDTO> findAccountNotifications(Long accountId) {
        ArrayList<NotificationDTO>  accountNotifications= new ArrayList<>();
        for(Notification accountNotification:notificationRepository.findAccountNotifications(accountId)){
            accountNotifications.add(new NotificationDTO(accountNotification));
        }
        return accountNotifications;
    }

    public NotificationDTO setSeen(Long notificationId) {
        Notification notificationToUpdate;
        try {
            notificationToUpdate = new Notification(findOne(notificationId)); // this will throw ResponseStatusException if student is not found
            notificationToUpdate.setSeen(true);
            notificationRepository.save(notificationToUpdate);
            notificationRepository.flush();
            return new NotificationDTO(notificationToUpdate);
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }
}
