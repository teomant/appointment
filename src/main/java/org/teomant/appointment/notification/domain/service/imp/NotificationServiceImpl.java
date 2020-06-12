package org.teomant.appointment.notification.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.notification.domain.repository.NotificationRepository;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.user.domain.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void create(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void markDelivered(Long notificationId, User currentUser) {
        Notification stored = notificationRepository.findById(notificationId);

        if (!stored.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException();
        }

        stored.setDelivered(true);
        notificationRepository.save(stored);
    }

    @Override
    public List<Notification> findByUser(Long userId, boolean includeDelivered) {
        return notificationRepository.findByUser(userId, includeDelivered);
    }
}
