package org.teomant.appointment.scheduling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.service.AppointmentService;
import org.teomant.appointment.notification.domain.service.NotificationService;
import org.teomant.appointment.user.domain.model.User;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final AppointmentService appointmentService;
    private final NotificationService notificationService;

    private final List<Appointment> appointments = new LinkedList<>();

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void getNextPortionOfAppointments() {
        log.info("filling list");
        appointments.clear();
        appointments.addAll(appointmentService.getUndoneAppointmentsTill(OffsetDateTime.now().plusMinutes(30)));
    }

    @Transactional(rollbackOn = JpaSystemException.class)
    @Scheduled(fixedRate = 5 * 1000)
    public void createNotifications() {
        log.info("creating notifications");
        List<Appointment> toCreateNotifications = appointments.stream()
                .filter(appointment -> appointment.getTill().isBefore(OffsetDateTime.now()))
                .collect(Collectors.toList());
        appointments.removeAll(toCreateNotifications);

        toCreateNotifications.forEach(appointment -> {
            notificationService.createFromAppointment(appointment);

            Set<User> votedUsers = new HashSet<>();
            appointment.getOptions().forEach(option -> {
                option.getVotes().stream()
                        .filter(vote -> vote.getUser() != null)
                        .forEach(vote -> votedUsers.add(vote.getUser()));
            });

            notificationService.createFromVoters(votedUsers, appointment);

            appointmentService.markDone(appointment);
        });

    }
}
