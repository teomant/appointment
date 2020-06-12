package org.teomant.appointment.appointment.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.domain.service.AppointmentService;

import java.time.OffsetDateTime;
import java.util.List;

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
                || !appointment.getUser().getId().equals(update.getUser().getId())
                || appointment.isDone()) {
            throw new IllegalArgumentException();
        }

        appointment.setComment(update.getComment());
        appointment.setTill(update.getTill());
        appointment.setLongitude(update.getLongitude());
        appointment.setLatitude(update.getLatitude());
        appointment.setOptions(update.getOptions());

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till) {
        return appointmentRepository.getUndoneAppointmentsTill(till);
    }

    @Override
    public void markDone(Appointment appointment) {
        Appointment stored = get(appointment.getId());
        stored.setDone(true);

        appointmentRepository.save(stored);
    }
}
