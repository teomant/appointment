package org.teomant.appointment.appointment.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.model.AppointmentEntity;

@Repository
public interface AppointmentEntityJpaRepository extends JpaRepository<AppointmentEntity, Long> {
}
