package org.teomant.appointment.vote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("org.teomant.appointment.vote.persistance.repository")
public class VoteConfig {
}
