package org.teomant.appointment.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.teomant.appointment.security.persistance.repository")
public class SecurityPersistanceConfig {
}
