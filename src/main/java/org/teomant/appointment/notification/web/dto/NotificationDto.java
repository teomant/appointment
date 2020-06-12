package org.teomant.appointment.notification.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
    private Long id;
    private Long appointmentId;
    private String comment;
}
