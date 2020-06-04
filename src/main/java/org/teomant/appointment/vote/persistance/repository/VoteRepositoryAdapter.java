package org.teomant.appointment.vote.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.persistance.mapping.OptionMapper;
import org.teomant.appointment.appointment.persistance.repository.OptionEntityJpaRepository;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.repository.VoteRepository;
import org.teomant.appointment.vote.persistance.mapping.VoteMapper;
import org.teomant.appointment.vote.persistance.model.VoteEntity;

@Repository
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepository {

    private final VoteEntityJpaRepository voteEntityJpaRepository;
    private final OptionEntityJpaRepository optionEntityJpaRepository;
    private final VoteMapper voteMapper = new VoteMapper();
    private final OptionMapper optionMapper = new OptionMapper();

    @Override
    public Vote save(Vote vote) {
        VoteEntity toSave = voteMapper.toEntity(vote);
        toSave.setOption(optionEntityJpaRepository.findById(vote.getOption().getId()).orElseThrow(IllegalArgumentException::new));
        VoteEntity saved = voteEntityJpaRepository.save(toSave);
        Vote response = voteMapper.toModel(saved);
        response.setOption(optionMapper.toModel(saved.getOption()));
        return response;
    }
}
