package org.teomant.appointment.appointment.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.domain.service.AppointmentService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment get(Long id) {
        return appointmentRepository.get(id);
    }

    @Override
    public Appointment update(Appointment update) {
        Appointment appointment = get(update.getId());
        appointment.setComment(update.getComment());
        appointment.setTill(update.getTill());
        appointment.setLongitude(update.getLongitude());
        appointment.setLatitude(update.getLatitude());
        appointment.setOptions(update.getOptions());

        return appointmentRepository.save(appointment);
    }
}
