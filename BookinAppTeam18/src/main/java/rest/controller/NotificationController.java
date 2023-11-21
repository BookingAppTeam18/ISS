package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.domain.Notification;
import rest.service.NotificationService;

import java.util.Collection;


@RestController
@RequestMapping("/api/notification")
public class NotificationController {

        @Autowired
        private NotificationService notificationService;

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Collection<Notification>> getNotifications() {
            Collection<Notification> notifications = notificationService.findAll();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Notification> getNotification(@PathVariable("id") Long id) {
            Notification notification = notificationService.findOne(id);

            if (notification == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(notification, HttpStatus.OK);
        }

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws Exception {
            Notification savedNotification = notificationService.create(notification);
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        }

        @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification, @PathVariable Long id)
                throws Exception {
            notification.setId(id);
            Notification updatedNotification = notificationService.update(notification);

            if (updatedNotification == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
        }

        @DeleteMapping(value = "/{id}")
        public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
            notificationService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}

