package org.teomant.appointment.vote.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

@Repository
public interface VoteEntityJpaRepository extends JpaRepository<VoteEntity, Long> {
}
