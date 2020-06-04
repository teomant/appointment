package org.teomant.appointment.appointment.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;

import java.util.List;

@Repository
public interface OptionEntityJpaRepository extends JpaRepository<OptionEntity, Long> {
    List<OptionEntity> findByAppointmentId(Long id);
}
