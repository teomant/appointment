package org.teomant.appointment.vote.web.mapping;

import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.model.VoteEnum;
import org.teomant.appointment.vote.web.dto.VoteDto;
import org.teomant.appointment.vote.web.dto.VoteRequestDto;

public class VoteDtoMapper {

    public Vote fromCreateToModel(VoteRequestDto dto) {
        Vote model = new Vote();
        model.setComment(dto.getComment());
        model.setType(VoteEnum.valueOf(dto.getType()));
        Option option = new Option();
        option.setId(dto.getOptionId());
        model.setOption(option);

        return model;
    }

    public VoteDto fromModelToDto(Vote model) {
        VoteDto dto = new VoteDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setType(model.getType().name());

        return dto;
    }
}
