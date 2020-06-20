package org.teomant.appointment.notification.persistance.mapping;

import org.teomant.appointment.appointment.persistance.mapping.AppointmentMapper;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.notification.persistance.model.NotificationEntity;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.model.UserEntity;

public class NotificationMapper {
    private final UserMapper userMapper = new UserMapper();
    private final AppointmentMapper appointmentMapper = new AppointmentMapper();

    public Notification toModel(NotificationEntity entity) {
        Notification model = new Notification();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setAppointment(appointmentMapper.toModel(entity.getAppointment()));
        model.setUser(userMapper.toModel(entity.getUser()));
        model.setDelivered(entity.isDelivered());

        return model;
    }

    public NotificationEntity toEntity(Notification model) {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setDelivered(model.isDelivered());

        UserEntity userEntity = new SiteUserEntity();
        userEntity.setId(model.getUser().getId());
        entity.setUser(userEntity);

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setId(model.getUser().getId());
        entity.setAppointment(appointmentEntity);

        return entity;
    }
}
