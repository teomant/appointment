package org.teomant.appointment.scheduling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final AppointmentRepository appointmentRepository;

    private final List<Appointment> appointments = new LinkedList<>();

    @Transactional
    @Scheduled(fixedRate = 30 * 60 * 1000)
    private void getNextPortionOfAppointments() {
        log.info("filling list");
        appointments.addAll(appointmentRepository.getUndoneAppointmentsTill(OffsetDateTime.now().plusMinutes(30)));
    }

    //TODO notification creation
    @Transactional
    @Scheduled(fixedRate = 5 * 1000)
    private void createNotifications() {
        log.info("creating notifications");
        appointments.stream()
                .filter(appointment -> appointment.getTill().isBefore(OffsetDateTime.now()));
    }
}
