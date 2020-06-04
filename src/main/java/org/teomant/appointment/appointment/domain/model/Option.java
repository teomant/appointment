package org.teomant.appointment.appointment.domain.model;

import lombok.Data;
import org.teomant.appointment.vote.domain.model.Vote;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class Option {
    private Long id;
    private String comment;
    private OffsetDateTime dateTime;
    private Appointment appointment;
    private Set<Vote> votes;
}
