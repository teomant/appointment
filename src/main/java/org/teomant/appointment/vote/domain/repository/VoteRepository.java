package org.teomant.appointment.vote.domain.repository;

import org.teomant.appointment.vote.domain.model.Vote;

public interface VoteRepository {

    public Vote save(Vote vote);
}
