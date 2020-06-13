package org.teomant.appointment.security.persistance.mapper;

import org.teomant.appointment.security.domain.model.Privilege;
import org.teomant.appointment.security.persistance.model.PrivilegeEntity;

public class PrivilegeMapper {

    public Privilege toModel(PrivilegeEntity entity) {
        Privilege model = new Privilege();
        model.setId(entity.getId());
        model.setAction(entity.getAction());
        model.setEntity(entity.getEntity());

        return model;
    }
}
