package org.teomant.appointment.appointment.domain.model;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Appointment {
    private Long id;
    private String comment;
    private double latitude;
    private double longitude;
    private OffsetDateTime created;
    private OffsetDateTime till;
    private List<Option> options;
}
