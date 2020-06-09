package org.teomant.appointment.vote.domain.model;

import lombok.Data;
import org.teomant.appointment.user.domain.model.User;

@Data
public class Vote {
    private Long id;
    private String comment;
    private VoteEnum type;
    private Long optionId;
    private User user;
}
