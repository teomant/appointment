package org.teomant.appointment.appointment.persistance.mapping;

import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.vote.persistance.mapping.VoteMapper;

import java.util.stream.Collectors;

public class OptionMapper {

    private final VoteMapper voteMapper = new VoteMapper();

    public Option toModel(OptionEntity entity) {
        Option model = new Option();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setDateTime(entity.getDateTime());
        model.setVotes(entity.getVotes().stream()
                .map(voteMapper::toModel)
                .collect(Collectors.toSet()));

        return model;
    }

    public OptionEntity toEntity(Option model) {
        OptionEntity entity = new OptionEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setDateTime(model.getDateTime());
        entity.setVotes(model.getVotes().stream()
                .map(voteMapper::toEntity)
                .peek(voteEntity -> voteEntity.setOption(entity))
                .collect(Collectors.toSet()));

        return entity;
    }
}
