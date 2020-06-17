package org.teomant.appointment.user.persistance.mapper;

import org.teomant.appointment.security.persistance.mapper.RoleMapper;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;

import java.util.stream.Collectors;

public class SiteUserMapper {

    private final RoleMapper roleMapper = new RoleMapper();

    public User toModel(SiteUserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        if (entity.getRoles() != null) {
            model.setRoles(entity.getRoles().stream().map(roleMapper::toModel).collect(Collectors.toList()));
        }
        model.setPassword(entity.getPassword());
        return model;
    }

    public SiteUserEntity toEntity(User model) {
        SiteUserEntity entity = new SiteUserEntity();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());

        return entity;
    }
}
