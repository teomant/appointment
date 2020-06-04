package org.teomant.appointment.appointment.persistance.mapping;

import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;

import java.util.stream.Collectors;

public class AppointmentMapper {

    private final OptionMapper optionMapper = new OptionMapper();

    public Appointment toModel(AppointmentEntity entity) {
        Appointment model = new Appointment();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setCreated(entity.getCreated());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setTill(entity.getTill());
        model.setOptions(entity.getOptions().stream().map(optionMapper::toModel).collect(Collectors.toSet()));

        return model;
    }

    public AppointmentEntity toEntity(Appointment model) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(model.getId());
        entity.setComment(model.getComment());
        entity.setCreated(model.getCreated());
        entity.setLatitude(model.getLatitude());
        entity.setLongitude(model.getLongitude());
        entity.setTill(model.getTill());
        entity.setOptions(model.getOptions().stream()
                .map(optionMapper::toEntity)
                .peek(optionEntity -> optionEntity.setAppointment(entity))
                .collect(Collectors.toSet()));

        return entity;
    }
}
