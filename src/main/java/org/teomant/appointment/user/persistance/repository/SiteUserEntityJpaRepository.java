package org.teomant.appointment.user.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.persistance.model.SiteUserEntity;

@Repository
public interface SiteUserEntityJpaRepository extends JpaRepository<SiteUserEntity, Long> {

    public SiteUserEntity findByUsername(String username);
}
