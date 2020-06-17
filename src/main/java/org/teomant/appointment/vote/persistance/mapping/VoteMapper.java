package org.teomant.appointment.vote.persistance.mapping;

import org.teomant.appointment.user.persistance.mapper.SiteUserMapper;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

public class VoteMapper {
    private final SiteUserMapper siteUserMapper = new SiteUserMapper();

    public Vote toModel(VoteEntity entity) {
        Vote model = new Vote();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setType(entity.getType());

        if (entity.getUser() != null) {
            model.setUser(siteUserMapper.toModel(entity.getUser()));
        }

        return model;
    }

    public VoteEntity toEntity(Vote model) {
        VoteEntity entity = new VoteEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setType(model.getType());

        if (model.getUser() != null) {
            entity.setUser(siteUserMapper.toEntity(model.getUser()));
        }

        return entity;
    }
}
