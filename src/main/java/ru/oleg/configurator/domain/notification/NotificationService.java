package ru.oleg.configurator.domain.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.oleg.configurator.domain.notification.dto.Notification;

@Service
@AllArgsConstructor
public class NotificationService {
    public Notification getNotification() {
        return new Notification();
    }

    public Notification updateNotification(@RequestBody Notification notification) {
        return new Notification();
    }
}
