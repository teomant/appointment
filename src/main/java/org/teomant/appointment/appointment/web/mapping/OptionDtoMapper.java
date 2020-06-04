package org.teomant.appointment.appointment.web.mapping;

import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.web.dto.OptionDto;
import org.teomant.appointment.vote.web.mapping.VoteDtoMapper;

import java.util.Collections;
import java.util.stream.Collectors;

public class OptionDtoMapper {

    private final VoteDtoMapper voteDtoMapper = new VoteDtoMapper();

    public Option fromDtoToModel(OptionDto dto) {
        Option model = new Option();
        model.setId(dto.getId());
        model.setComment(dto.getComment());
        model.setDateTime(dto.getDateTime());
        model.setVotes(Collections.EMPTY_SET);
        return model;
    }

    public OptionDto fromModelToDto(Option model) {
        OptionDto dto = new OptionDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setDateTime(model.getDateTime());
        dto.setVotes(model.getVotes().stream().map(voteDtoMapper::fromModelToDto).collect(Collectors.toSet()));

        return dto;
    }
}
