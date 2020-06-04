package org.teomant.appointment.vote.persistance.mapping;

import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

public class VoteMapper {

    public Vote toModel(VoteEntity entity) {
        Vote model = new Vote();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setType(entity.getType());

        return model;
    }

    public VoteEntity toEntity(Vote model) {
        VoteEntity entity = new VoteEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setType(model.getType());

        return entity;
    }
}
