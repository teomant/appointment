package org.teomant.appointment.appointment.web.mapping;

import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.web.dto.AppointmentResponseDto;
import org.teomant.appointment.appointment.web.dto.OptionDto;

public class OptionDtoMapper {

    public Option fromDtoToModel(OptionDto dto) {
        Option model = new Option();
        model.setId(dto.getId());
        model.setComment(dto.getComment());
        model.setDateTime(dto.getDateTime());

        return model;
    }

    public OptionDto fromModelToDto(Option model) {
        OptionDto dto = new OptionDto();
        dto.setId(model.getId());
        dto.setComment(model.getComment());
        dto.setDateTime(model.getDateTime());

        return dto;
    }
}
