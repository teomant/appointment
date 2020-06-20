package org.teomant.appointment.notification.domain.service;

import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.User;

import java.util.Collection;
import java.util.List;

public interface NotificationService {

    void createFromAppointment(Appointment appointment);

    void createFromVoters(Collection<User> users, Appointment appointment);

    void markDelivered(Long notificationId, SiteUser currentSiteUser);

    List<Notification> findByUser(SiteUser siteUser, Boolean includeDelivered);

}
