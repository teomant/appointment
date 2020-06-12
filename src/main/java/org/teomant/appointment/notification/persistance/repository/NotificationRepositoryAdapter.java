package org.teomant.appointment.notification.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.notification.domain.repository.NotificationRepository;
import org.teomant.appointment.notification.persistance.mapping.NotificationMapper;
import org.teomant.appointment.user.persistance.repository.UserEntityJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final NotificationEntityJpaRepository notificationEntityJpaRepository;
    private final NotificationMapper notificationMapper = new NotificationMapper();
    private final UserEntityJpaRepository userEntityJpaRepository;

    @Override
    public void save(Notification notification) {
        notificationEntityJpaRepository.save(notificationMapper.toEntity(notification));
    }

    @Override
    public void saveAll(List<Notification> notifications) {
        notificationEntityJpaRepository.saveAll(notifications.stream().map(notificationMapper::toEntity).collect(Collectors.toList()));
    }

    @Override
    public Notification findById(Long id) {
        return notificationMapper.toModel(notificationEntityJpaRepository.getOne(id));
    }

    @Override
    public List<Notification> findByUser(Long userId, boolean includeDelivered) {
        return notificationEntityJpaRepository.findByUserAndDelivered(userEntityJpaRepository.getOne(userId), includeDelivered).stream()
                .map(notificationMapper::toModel)
                .collect(Collectors.toList());
    }
}
