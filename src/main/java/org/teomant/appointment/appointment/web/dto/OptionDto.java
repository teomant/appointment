package org.teomant.appointment.appointment.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.teomant.appointment.vote.web.dto.VoteDto;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OptionDto {
    private Long id;
    private String comment;
    private OffsetDateTime dateTime;
    private List<VoteDto> votes;
}
