package org.teomant.appointment.user.domain.repository;

import org.teomant.appointment.user.domain.model.User;

public interface UserRepository {

    User findByUsername(String username);
}
