package org.teomant.appointment.notification.domain.service;

import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.user.domain.model.User;

import java.util.List;

public interface NotificationService {

    public void create(List<Notification> notifications);

    public void markDelivered(Long notificationId, User currentUser);

    public List<Notification> findByUser(Long userId, boolean includeDelivered);

}
