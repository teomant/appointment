package org.teomant.appointment.appointment.domain.service;

import org.teomant.appointment.appointment.domain.model.Appointment;

public interface AppointmentService {

    public Appointment create(Appointment appointment);

    public Appointment get(Long id);

    public Appointment update(Appointment appointment);

}
