package org.teomant.appointment.user.persistance.mapper;

import org.teomant.appointment.security.persistance.mapper.RoleMapper;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.TelegramBotUser;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.model.TelegramBotUserEntity;
import org.teomant.appointment.user.persistance.model.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {

    private final RoleMapper roleMapper = new RoleMapper();

    public TelegramBotUser toModel(TelegramBotUserEntity entity) {
        TelegramBotUser model = new TelegramBotUser();
        model.setId(entity.getId());
        model.setUsername(entity.getName() != null ? entity.getName() : entity.getLogin());
        model.setChatId(entity.getChatId());
        model.setLogin(entity.getLogin());
        return model;
    }

    public SiteUser toModel(SiteUserEntity entity) {
        SiteUser model = new SiteUser();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        if (entity.getRoles() != null) {
            model.setRoles(entity.getRoles().stream().map(roleMapper::toModel).collect(Collectors.toList()));
        }
        model.setPassword(entity.getPassword());
        return model;
    }

    public User toModel(UserEntity entity) {
        if (entity instanceof SiteUserEntity) {
            return toModel((SiteUserEntity) entity);
        }
        if (entity instanceof TelegramBotUserEntity) {
            return toModel((TelegramBotUserEntity) entity);
        }

        User model = new User();
        model.setId(entity.getId());
        return model;
    }

    public SiteUserEntity toEntity(SiteUser model) {
        SiteUserEntity entity = new SiteUserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());

        return entity;
    }

    public TelegramBotUserEntity toEntity(TelegramBotUser model) {
        TelegramBotUserEntity entity = new TelegramBotUserEntity();
        entity.setId(model.getId());
        entity.setName(model.getUsername());
        entity.setLogin(model.getLogin());

        return entity;
    }
}
