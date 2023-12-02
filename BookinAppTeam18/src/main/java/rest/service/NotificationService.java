package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.repository.NotificationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class NotificationService implements IService<NotificationDTO> {

    @Autowired
    private NotificationRepository notificationRepository;
    ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
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
            String value = bundle.getString("notFound");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
        }
        return new NotificationDTO(found.get());
    }

    @Override
    public NotificationDTO insert(NotificationDTO NotificationDTO){
        Notification notification = new Notification(NotificationDTO);
        try {
            notificationRepository.save(notification);
            notificationRepository.flush();
            return NotificationDTO;
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
        try {
            findOne(NotificationDTO.getId()); // this will throw ResponseStatusException if student is not found
            notificationRepository.save(notificationToUpdate);
            notificationRepository.flush();
            return NotificationDTO;
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
        Notification found = new Notification(findOne(id)); // this will throw StudentNotFoundException if student is not found
        notificationRepository.delete(found);
        notificationRepository.flush();
        return new NotificationDTO(found);
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
        notificationRepository.flush();
    }
}
