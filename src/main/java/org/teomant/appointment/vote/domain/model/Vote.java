package org.teomant.appointment.vote.domain.model;

import lombok.Data;

@Data
public class Vote {
    private Long id;
    private String comment;
    private VoteEnum type;
    private Long optionId;
}
