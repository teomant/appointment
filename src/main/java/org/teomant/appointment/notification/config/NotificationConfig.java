package org.teomant.appointment.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.teomant.appointment.notification.persistance.repository")
public class NotificationConfig {
}
