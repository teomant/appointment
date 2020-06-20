package org.teomant.appointment.vote.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.model.OptionEntity;
import org.teomant.appointment.appointment.persistance.repository.OptionEntityJpaRepository;
import org.teomant.appointment.user.persistance.model.UserEntity;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.repository.VoteRepository;
import org.teomant.appointment.vote.persistance.mapping.VoteMapper;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepository {

    private final VoteEntityJpaRepository voteEntityJpaRepository;
    private final OptionEntityJpaRepository optionEntityJpaRepository;
    private final VoteMapper voteMapper = new VoteMapper();

    @Override
    public Vote save(Vote vote) {

        OptionEntity option = optionEntityJpaRepository.findById(vote.getOptionId()).orElseThrow(IllegalArgumentException::new);
        if (option.getAppointment() == null || option.getAppointment().isDone()) {
            throw new IllegalArgumentException();
        }

        if (vote.getUser() != null) {
            UserEntity user = new UserEntity();
            user.setId(vote.getUser().getId());

            List<VoteEntity> byUser = voteEntityJpaRepository.findByUser(user);
            Optional<VoteEntity> sameVote = byUser.stream()
                    .filter(voteEntity -> voteEntity.getOption().getId().equals(vote.getOptionId())
                            && voteEntity.getType().equals(vote.getType())).findAny();
            if (sameVote.isPresent()) {
                return voteMapper.toModel(sameVote.get());
            }
        }

        VoteEntity toSave = voteMapper.toEntity(vote);
        toSave.setOption(option);
        VoteEntity saved = voteEntityJpaRepository.save(toSave);
        Vote response = voteMapper.toModel(saved);
        response.setOptionId(saved.getOption().getId());
        return response;
    }

    @Override
    public void delete(Vote vote) {
        VoteEntity voteEntity = voteEntityJpaRepository.findById(vote.getId()).orElseThrow(IllegalArgumentException::new);

        voteEntityJpaRepository.delete(voteEntity);
        voteEntityJpaRepository.flush();
    }

    @Override
    public Vote findById(Long id) {
        return voteMapper.toModel(voteEntityJpaRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }
}
