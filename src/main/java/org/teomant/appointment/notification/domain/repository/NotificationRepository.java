package org.teomant.appointment.notification.domain.repository;

import org.teomant.appointment.notification.domain.model.Notification;

import java.util.List;

public interface NotificationRepository {

    void save(Notification notification);

    void saveAll(List<Notification> notification);

    Notification findById(Long id);

    List<Notification> findByUser(Long userId, boolean includeDelivered);
}
