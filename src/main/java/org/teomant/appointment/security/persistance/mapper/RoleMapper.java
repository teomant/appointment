package org.teomant.appointment.security.persistance.mapper;

import org.teomant.appointment.security.domain.model.Role;
import org.teomant.appointment.security.persistance.model.RoleEntity;

import java.util.stream.Collectors;

public class RoleMapper {

    private final PrivilegeMapper privilegeMapper = new PrivilegeMapper();

    public Role toModel(RoleEntity entity) {
        Role model = new Role();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPrivileges(entity.getPrivileges().stream().map(privilegeMapper::toModel).collect(Collectors.toList()));

        return model;
    }
}
