package org.teomant.appointment.appointment.web.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.web.dto.OptionDto;
import org.teomant.appointment.vote.web.mapping.VoteDtoMapper;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptionDtoMapper {

    private final VoteDtoMapper voteDtoMapper;

    public Option fromDtoToModel(OptionDto dto) {
        Option model = new Option();
        model.setId(dto.getId());
        model.setComment(dto.getComment());
        model.setDateTime(dto.getDateTime());
        model.setVotes(Collections.EMPTY_LIST);
        return model;
    }

    public OptionDto fromModelToDto(Option model) {
        OptionDto dto = new OptionDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setDateTime(model.getDateTime());
        dto.setVotes(model.getVotes().stream().map(voteDtoMapper::fromModelToDto).collect(Collectors.toList()));

        return dto;
    }
}
