package org.teomant.appointment.notification.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.exception.AppointmentException;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.notification.domain.repository.NotificationRepository;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.security.service.RightChecker;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.User;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final RightChecker rightChecker;

    @Override
    public void createFromAppointment(Appointment appointment) {
        if (appointment.getUser() == null) {
            return;
        }

        Notification notification = new Notification();
        notification.setComment("creator notification");
        notification.setAppointment(appointment);
        notification.setUser(appointment.getUser());
        notification.setDelivered(false);

        notificationRepository.save(notification);
    }

    @Override
    public void createFromVoters(Collection<User> users, Appointment appointment) {
        users.forEach(user -> {
            Notification notification = new Notification();
            notification.setComment("voter notification");
            notification.setAppointment(appointment);
            notification.setUser(user);

            if (user instanceof SiteUser) {
                notification.setDelivered(false);
            }

            notificationRepository.save(notification);
        });
    }

    @Override
    public void markDelivered(Long notificationId, SiteUser currentSiteUser) {
        Notification stored = notificationRepository.findById(notificationId);

        if (!rightChecker.checkCanPerform(EntityNameEnum.NOTIFICATION, ActionNameEnum.MODIFY, (SiteUser) stored.getUser(), currentSiteUser)) {
            throw new AppointmentException("Can`t do");
        }

        stored.setDelivered(true);
        notificationRepository.save(stored);
    }

    @Override
    public List<Notification> findByUser(SiteUser siteUser, Boolean includeDelivered) {
        return notificationRepository.findByUser(siteUser, includeDelivered);
    }
}
