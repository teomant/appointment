package org.teomant.appointment.user.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.persistance.model.ClientEntity;
import org.teomant.appointment.user.persistance.model.ClientUserEntity;

@Repository
public interface ClientUserEntityJpaRepository extends JpaRepository<ClientUserEntity, Long> {

    ClientUserEntity findByClientUserIdAndClientEntity(Long id, ClientEntity clientEntity);

}
