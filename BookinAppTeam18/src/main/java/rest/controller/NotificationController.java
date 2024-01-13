package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.service.NotificationService;

import java.util.Collection;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    //ALL Notifications
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getNotifications() {
        Collection<NotificationDTO> notifications = notificationService.findAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    //notification by id
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        NotificationDTO notification = notificationService.findOne(id);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //unseen notifications for specific account
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "unseen/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getAccountUnseenNotifications(@PathVariable("id") Long accountId) {
        Collection<NotificationDTO> notification = notificationService.findAccountUnseenNotifications(accountId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }


    //all notifications for specific account
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getAccountNotifications(@PathVariable("id") Long accountId) {
        Collection<NotificationDTO> notification = notificationService.findAccountNotifications(accountId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //set seen Notification
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @GetMapping(value = "seen/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> setSeenNotification(@PathVariable("id") Long notificationId) {
        NotificationDTO notification = notificationService.setSeen(notificationId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //Create Notification
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification) throws Exception {
        NotificationDTO savedNotification = notificationService.insert(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    //Update Notification
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notification, @PathVariable Long id)
            throws Exception {
        notification.setId(id);
        NotificationDTO updatedNotification = notificationService.update(notification);

        if (updatedNotification == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    //Delete Notification
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // EndPoints for Web Socket sending and receive notifications


    // REST enpoint
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','GUEST')")
    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody NotificationDTO notification) {
        this.notificationService.sendMessage(notification);
        return new ResponseEntity<>(notification, new HttpHeaders(), HttpStatus.OK);
    }


    /*
     * WebSockets endpoint
     *
     * Kao sto smo koristili @RequestMapping za RestController, @MessageMapping se koristi za websocket-e
     *
     * Poruka ce biti poslata svim klijentima koji su pretplatili na /socket-publisher topic,
     * a poruka koja im se salje je messageConverted (simpMessagingTemplate.convertAndSend metoda).
     *
     * Na ovaj endpoint klijenti salju poruke, ruta na koju klijenti salju poruke je /send/message (parametar @MessageMapping anotacije)
     *
     *//*
    @MessageMapping("/send/message")
    public NotificationDTO broadcastNotification(Notification notification) {
        NotificationDTO messageConverted = parseMessage(message);

        if (messageConverted != null) {
            if (messageConverted.containsKey("toId") && messageConverted.get("toId") != null
                    && !messageConverted.get("toId").equals("")) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("toId"),
                        messageConverted);
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("fromId"),
                        messageConverted);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
            }
        }

        return messageConverted;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }*/
}

