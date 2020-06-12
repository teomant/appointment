package org.teomant.appointment.appointment.web.mapping;

import org.springframework.security.core.context.SecurityContextHolder;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.web.dto.AppointmentRequestDto;
import org.teomant.appointment.appointment.web.dto.AppointmentResponseDto;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.UserEntity;

import java.util.stream.Collectors;

public class AppointmentDtoMapper {

    private final OptionDtoMapper optionDtoMapper = new OptionDtoMapper();
    private final UserMapper userMapper = new UserMapper();

    public Appointment fromDtoToModel(AppointmentRequestDto dto, Long id) {
        Appointment model = new Appointment();
        model.setId(id);
        model.setComment(dto.getComment());
        model.setLatitude(dto.getLatitude());
        model.setLongitude(dto.getLongitude());
        model.setTill(dto.getTill());
        model.setOptions(dto.getOptions().stream().map(optionDtoMapper::fromDtoToModel).collect(Collectors.toList()));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            model.setUser(userMapper.toModel((UserEntity) principal));
        }

        return model;
    }

    public AppointmentResponseDto fromModelToDto(Appointment model) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setLatitude(model.getLatitude());
        dto.setLongitude(model.getLongitude());
        dto.setTill(model.getTill());
        dto.setOptions(model.getOptions().stream().map(optionDtoMapper::fromModelToDto).collect(Collectors.toList()));
        dto.setCreatorName(model.getUser() != null ? model.getUser().getUsername() : "");
        dto.setDone(model.isDone());

        return dto;
    }
}
