package org.teomant.appointment.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.teomant.appointment.user.persistance.repository")
public class UserConfig {
}
