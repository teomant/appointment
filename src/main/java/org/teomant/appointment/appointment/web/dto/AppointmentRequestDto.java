package org.teomant.appointment.appointment.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class AppointmentRequestDto {
    private String comment;
    private double latitude;
    private double longitude;
    private OffsetDateTime till;
    private Set<OptionDto> options;
}
