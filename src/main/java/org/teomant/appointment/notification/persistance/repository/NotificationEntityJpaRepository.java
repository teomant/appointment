package org.teomant.appointment.notification.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.notification.persistance.model.NotificationEntity;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;

import java.util.List;

@Repository
public interface NotificationEntityJpaRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserAndDelivered(SiteUserEntity userEntity, boolean delivered);

    List<NotificationEntity> findByUser(SiteUserEntity userEntity);
}
