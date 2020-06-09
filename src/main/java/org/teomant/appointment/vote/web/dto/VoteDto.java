package org.teomant.appointment.vote.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDto {
    private Long id;
    private String comment;
    private String type;
    private String voterName;
}
