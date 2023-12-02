package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getNotifications() {
        Collection<NotificationDTO> notifications = notificationService.findAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    //notification by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        NotificationDTO notification = notificationService.findOne(id);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //unseen notifications for specific account
    @GetMapping(value = "unseen/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getAccountUnseenNotifications(@PathVariable("id") Long accountId) {
        Collection<NotificationDTO> notification = notificationService.findAccountUnseenNotifications(accountId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }


    //all notifications for specific account
    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getAccountNotifications(@PathVariable("id") Long accountId) {
        Collection<NotificationDTO> notification = notificationService.findAccountNotifications(accountId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //set seen Notification
    @GetMapping(value = "seen/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> setSeenNotification(@PathVariable("id") Long notificationId) {
        NotificationDTO notification = notificationService.setSeen(notificationId);

        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    //Create Notification
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification) throws Exception {
        NotificationDTO savedNotification = notificationService.insert(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    //Update Notification
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

