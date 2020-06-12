package org.teomant.appointment.appointment.domain.repository;

import org.teomant.appointment.appointment.domain.model.Appointment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    Appointment get(Long id);

    List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till);
}
