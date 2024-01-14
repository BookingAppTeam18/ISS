package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.repository.AccommodationRepository;
import rest.repository.AccountRepository;
import rest.repository.NotificationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationService implements IService<NotificationDTO> {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
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


    public void sendMessage(NotificationDTO notification) {
        if(notification.getMessage().contains("Comment on Account")){
            notification.setMessage(generateCommentOnAccountMessage(notification));
        }
        if(notification.getMessage().contains("Comment on Accommodation")){
            notification.setMessage(generateCommentOnAccommodationMessage(notification));
        }
        if(notification.getMessage().contains("Reservation Request")){
            notification.setMessage(generateReservationRequestMessage(notification));
        }
        if(notification.getMessage().contains("Reservation Cancel")){
            notification.setMessage(generateReservationCancelMessage(notification));
        }
        if(notification.getMessage().contains("Reservation Answer")){
            notification.setMessage(generateReservationAnswerMessage(notification));
        }
        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + notification.getAccountId(), notification);
        insert(notification);
    }

    private String generateReservationAnswerMessage( NotificationDTO notification) {
        return "message";
    }

    private String generateReservationCancelMessage( NotificationDTO notification) {
        return "message";
    }

    private String generateReservationRequestMessage( NotificationDTO notification) {
        String message = notification.getMessage();
        String[] words = message.split("\\s+");
        String accommodationIdStr = words[words.length - 1];
        Long accommodationId = Long.parseLong(accommodationIdStr);
        String accountIdStr = words[words.length - 2];
        Long accountId = Long.parseLong(accountIdStr);
        Optional<Account> accountOptional  = this.accountRepository.findById(accountId);
        Optional<Accommodation> accommodationOptional  = this.accommodationRepository.findById(accommodationId);
        if (accommodationOptional.isPresent() && accountOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();
            Account account = accountOptional.get();
            return "User: "+ account.getFirstName()+" "+account.getLastName() +" made a reservation on your accommodation: " + accommodation.getName();
        } else {
            return "Error";
        }
    }

    private String generateCommentOnAccommodationMessage( NotificationDTO notification) {
        String message = notification.getMessage();
        String[] words = message.split("\\s+");
        String accommodationIdStr = words[words.length - 1];
        Long accommodationId = Long.parseLong(accommodationIdStr);
        Optional<Accommodation> accommodationOptional  = this.accommodationRepository.findById(accommodationId);

        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();
                return "User Commented on your accommodation: " + accommodation.getName();
        } else {
            return "Error";
        }


    }

    private String generateCommentOnAccountMessage( NotificationDTO notification) {
        return "User commented on your account";
    }
}
