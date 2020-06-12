package org.teomant.appointment.notification.web.mapping;

import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.notification.web.dto.NotificationDto;

public class NotificationDtoMapper {

    public NotificationDto fromModelToDto(Notification model) {
        NotificationDto dto = new NotificationDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setAppointmentId(model.getAppointment().getId());

        return dto;
    }
}
