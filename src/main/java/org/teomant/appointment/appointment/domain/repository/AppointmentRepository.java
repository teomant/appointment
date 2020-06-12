package org.teomant.appointment.appointment.domain.repository;

import org.teomant.appointment.appointment.domain.model.Appointment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepository {

    public Appointment save(Appointment appointment);

    public Appointment get(Long id);

    public List<Appointment> getUndoneAppointmentsTill(OffsetDateTime till);
}
