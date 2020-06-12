package org.teomant.appointment.notification.domain.model;

import lombok.Data;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.user.domain.model.User;

@Data
public class Notification {
    boolean delivered;
    private Long id;
    private String comment;
    private Appointment appointment;
    private User user;
}
