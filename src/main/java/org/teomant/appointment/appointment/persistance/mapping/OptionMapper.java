package org.teomant.appointment.appointment.persistance.mapping;

import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;

public class OptionMapper {

    public Option toModel(OptionEntity entity) {
        Option model = new Option();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setDateTime(entity.getDateTime());
        return model;
    }

    public OptionEntity toEntity(Option model) {
        OptionEntity entity = new OptionEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setDateTime(model.getDateTime());

        return entity;
    }
}
