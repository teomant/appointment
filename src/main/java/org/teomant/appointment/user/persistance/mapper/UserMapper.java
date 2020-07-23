package org.teomant.appointment.user.persistance.mapper;

import org.teomant.appointment.security.persistance.mapper.RoleMapper;
import org.teomant.appointment.user.domain.model.ClientUser;
import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.persistance.model.ClientUserEntity;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;
import org.teomant.appointment.user.persistance.model.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {

    private final RoleMapper roleMapper = new RoleMapper();

    public ClientUser toModel(ClientUserEntity entity) {
        ClientUser model = new ClientUser();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername() != null ? entity.getUsername() : "Аноним");
        model.setClientId(entity.getClientUserId());
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
        if (entity instanceof ClientUserEntity) {
            return toModel((ClientUserEntity) entity);
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

    public ClientUserEntity toEntity(ClientUser model) {
        ClientUserEntity entity = new ClientUserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());

        return entity;
    }
}
