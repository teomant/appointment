package org.teomant.appointment.user.persistance.mapper;

import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.persistance.model.UserEntity;

public class UserMapper {

    public User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());

        return model;
    }

    public UserEntity toEntity(User model) {
        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());

        return entity;
    }
}
