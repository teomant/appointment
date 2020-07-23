package org.teomant.appointment.user.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.persistance.model.ClientEntity;

@Repository
public interface ClientEntityJpaRepository extends JpaRepository<ClientEntity, Long> {

    public ClientEntity findByUsername(String username);
}
