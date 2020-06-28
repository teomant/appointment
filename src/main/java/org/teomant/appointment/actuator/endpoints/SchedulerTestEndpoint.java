package org.teomant.appointment.actuator.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.teomant.appointment.scheduling.service.SchedulingService;

@Component
@Endpoint(id = "scheduler-test")
@RequiredArgsConstructor
public class SchedulerTestEndpoint {

    private final SchedulingService schedulingService;

    @WriteOperation
    public void configureFeature() {
        schedulingService.getNextPortionOfAppointments();
        schedulingService.createNotifications();
    }
}
