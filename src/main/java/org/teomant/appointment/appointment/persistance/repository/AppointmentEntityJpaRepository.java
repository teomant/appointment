package org.teomant.appointment.appointment.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AppointmentEntityJpaRepository extends JpaRepository<AppointmentEntity, Long> {

    public List<AppointmentEntity> findByTillBeforeAndDoneFalse(OffsetDateTime till);
}
