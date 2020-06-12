package org.teomant.appointment.appointment.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.domain.service.AppointmentService;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment create(Appointment appointment) {
        validateCreate(appointment);

        appointment.setCreated(OffsetDateTime.now());
        appointment.setDone(false);

        return appointmentRepository.save(appointment);
    }

    private void validateCreate(Appointment appointment) {
        if (appointment.getTill().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Appointment get(Long id) {
        return appointmentRepository.get(id);
    }

    @Override
    public Appointment update(Appointment update) {
        Appointment appointment = get(update.getId());

        if (appointment.getUser() == null || update.getUser() == null
                || !appointment.getUser().getId().equals(update.getUser().getId())) {
            throw new IllegalArgumentException();
        }

        appointment.setComment(update.getComment());
        appointment.setTill(update.getTill());
        appointment.setLongitude(update.getLongitude());
        appointment.setLatitude(update.getLatitude());
        appointment.setOptions(update.getOptions());

        return appointmentRepository.save(appointment);
    }
}
