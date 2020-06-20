package org.teomant.appointment.notification.domain.repository;

import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.user.domain.model.SiteUser;

import java.util.List;

public interface NotificationRepository {

    void save(Notification notification);

    void saveAll(List<Notification> notification);

    Notification findById(Long id);

    List<Notification> findByUser(SiteUser siteUser, Boolean includeDelivered);
}
