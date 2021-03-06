package org.teomant.appointment.appointment.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.appointment.domain.service.AppointmentService;
import org.teomant.appointment.exception.AppointmentException;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.security.service.RightChecker;
import org.teomant.appointment.user.domain.model.SiteUser;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RightChecker rightChecker;

    @Override
    public Appointment create(Appointment appointment) {
        validateCreate(appointment);

        appointment.setCreated(OffsetDateTime.now());
        appointment.setDone(false);

        return appointmentRepository.save(appointment);
    }

    private void validateCreate(Appointment appointment) {
        if (appointment.getTill().isBefore(OffsetDateTime.now())) {
            throw new AppointmentException("Till before current");
        }
    }

    @Override
    @Transactional
    public Appointment get(Long id) {
        return appointmentRepository.get(id);
    }

    @Override
    public Appointment update(Appointment update, SiteUser currentSiteUser) {
        Appointment appointment = get(update.getId());

        if (!rightChecker.checkCanPerform(EntityNameEnum.APPOINTMENT, ActionNameEnum.MODIFY, (SiteUser) appointment.getUser(), currentSiteUser)
                || appointment.isDone()) {
            throw new AppointmentException("Can`t update");
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
