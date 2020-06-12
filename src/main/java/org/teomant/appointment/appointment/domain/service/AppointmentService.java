package org.teomant.appointment.appointment.domain.service;

import org.teomant.appointment.appointment.domain.model.Appointment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment create(Appointment appointment);

    Appointment get(Long id);

    Appointment update(Appointment appointment);

    List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till);

    void markDone(Appointment appointment);
}
