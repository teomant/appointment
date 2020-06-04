package org.teomant.appointment.appointment.domain.repository;

import org.teomant.appointment.appointment.domain.model.Appointment;

public interface AppointmentRepository {

    public Appointment save(Appointment appointment);

    public Appointment get(Long id);
}
