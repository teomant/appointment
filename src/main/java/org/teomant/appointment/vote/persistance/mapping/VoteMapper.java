package org.teomant.appointment.vote.persistance.mapping;

import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.TelegramBotUser;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.model.TelegramBotUserEntity;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

public class VoteMapper {
    private final UserMapper userMapper = new UserMapper();

    public Vote toModel(VoteEntity entity) {
        Vote model = new Vote();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setType(entity.getType());

        if (entity.getUser() != null) {
            if (entity.getUser() instanceof SiteUserEntity) {
                model.setUser(userMapper.toModel((SiteUserEntity) entity.getUser()));
            }
            if (entity.getUser() instanceof TelegramBotUserEntity) {
                model.setUser(userMapper.toModel((TelegramBotUserEntity) entity.getUser()));
            }

        }

        return model;
    }

    public VoteEntity toEntity(Vote model) {
        VoteEntity entity = new VoteEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setType(model.getType());

        if (model.getUser() != null) {
            if (model.getUser() instanceof SiteUser)
                entity.setUser(userMapper.toEntity((SiteUser) model.getUser()));
            if (model.getUser() instanceof TelegramBotUser)
                entity.setUser(userMapper.toEntity((TelegramBotUser) model.getUser()));
        }

        return entity;
    }
}
