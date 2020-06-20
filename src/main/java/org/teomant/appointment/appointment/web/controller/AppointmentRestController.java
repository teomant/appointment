package org.teomant.appointment.appointment.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.teomant.appointment.appointment.domain.service.AppointmentService;
import org.teomant.appointment.appointment.web.dto.AppointmentRequestDto;
import org.teomant.appointment.appointment.web.dto.AppointmentResponseDto;
import org.teomant.appointment.appointment.web.mapping.AppointmentDtoMapper;
import org.teomant.appointment.user.domain.model.SiteUser;

@RestController
@RequestMapping("/rest/appointment")
@RequiredArgsConstructor
public class AppointmentRestController {

    private final AppointmentService appointmentService;
    private final AppointmentDtoMapper appointmentDtoMapper;

    @PostMapping("/create")
    public AppointmentResponseDto createAppointment(@RequestBody AppointmentRequestDto create) {
        return appointmentDtoMapper.fromModelToDto(appointmentService.create(appointmentDtoMapper.fromDtoToModel(create, null)));
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getAppointment(@PathVariable Long id) {
        return appointmentDtoMapper.fromModelToDto(appointmentService.get(id));
    }

    @PostMapping("/{id}")
    public AppointmentResponseDto updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDto update) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SiteUser currentSiteUser = null;
        if (principal instanceof SiteUser) {
            currentSiteUser = (SiteUser) principal;
        }

        return appointmentDtoMapper.fromModelToDto(appointmentService.update(appointmentDtoMapper.fromDtoToModel(update, id), currentSiteUser));
    }
}
