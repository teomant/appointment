package org.teomant.appointment.appointment.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OptionDto {
    private Long id;
    private String comment;
    private OffsetDateTime dateTime;
}
