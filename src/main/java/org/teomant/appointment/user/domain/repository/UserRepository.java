package org.teomant.appointment.user.domain.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {

    UserDetails findByUsername(String username);
}
