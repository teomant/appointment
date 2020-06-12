package org.teomant.appointment.appointment.persistance.mapping;

import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;
import org.teomant.appointment.user.persistance.mapper.UserMapper;

import java.util.stream.Collectors;

public class AppointmentMapper {

    private final OptionMapper optionMapper = new OptionMapper();
    private final UserMapper userMapper = new UserMapper();

    public Appointment toModel(AppointmentEntity entity) {
        Appointment model = new Appointment();
        model.setId(entity.getId());
        model.setComment(entity.getComment());
        model.setCreated(entity.getCreated());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setTill(entity.getTill());
        model.setOptions(entity.getOptions().stream()
                .map(optionMapper::toModel)
                .collect(Collectors.toList()));
        model.setDone(entity.isDone());

        if (entity.getUser() != null) {
            model.setUser(userMapper.toModel(entity.getUser()));
        }

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
                .collect(Collectors.toList()));
        entity.setDone(model.isDone());

        if (model.getUser() != null) {
            entity.setUser(userMapper.toEntity(model.getUser()));
        }

        return entity;
    }
}
