package org.teomant.appointment.vote.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.user.persistance.model.UserEntity;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

import java.util.List;

@Repository
public interface VoteEntityJpaRepository extends JpaRepository<VoteEntity, Long> {
    List<VoteEntity> findByOption(OptionEntity optionEntity);

    List<VoteEntity> findByUser(UserEntity userEntity);
}
