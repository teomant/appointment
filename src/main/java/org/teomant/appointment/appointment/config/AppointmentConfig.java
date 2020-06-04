package org.teomant.appointment.appointment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.teomant.appointment.appointment.persistance.repository")
public class AppointmentConfig {
}
