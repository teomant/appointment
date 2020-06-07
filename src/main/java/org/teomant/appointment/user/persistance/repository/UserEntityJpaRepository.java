package org.teomant.appointment.user.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.persistance.model.UserEntity;

@Repository
public interface UserEntityJpaRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);
}
