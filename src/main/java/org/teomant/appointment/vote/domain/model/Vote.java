package org.teomant.appointment.vote.domain.model;

import lombok.Data;
import org.teomant.appointment.appointment.domain.model.Option;

@Data
public class Vote {
    private Long id;
    private String comment;
    private VoteEnum type;
    private Option option;
}
