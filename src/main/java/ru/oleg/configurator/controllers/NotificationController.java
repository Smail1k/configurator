package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.notification.NotificationService;
import ru.oleg.configurator.domain.notification.dto.Notification;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Notification> getNotification() {
        return ResponseEntity.ok(notificationService.getNotification());
    }

    @PatchMapping
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.updateNotification(notification));
    }
}
