package org.teomant.appointment.vote.domain.repository;

import org.teomant.appointment.vote.domain.model.Vote;

public interface VoteRepository {

    Vote save(Vote vote);

    void delete(Vote vote);

    Vote findById(Long id);
}
