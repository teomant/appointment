package org.teomant.appointment.vote.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDto {
    private String comment;
    private String type;
    private Long optionId;
}
