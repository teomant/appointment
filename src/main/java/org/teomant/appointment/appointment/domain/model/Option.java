package org.teomant.appointment.appointment.domain.model;

import lombok.Data;

import javax.persistence.Column;
import java.time.OffsetDateTime;

@Data
public class Option {
    private Long id;
    private String comment;
    private OffsetDateTime dateTime;
    private Appointment appointment;
}
