package org.teomant.appointment.security.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.security.persistance.model.RoleEntity;

@Repository
public interface RoleEntityJpaRepository extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findByName(String name);
}
