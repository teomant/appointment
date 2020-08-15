package org.teomant.appointment.vote.domain.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.exception.AppointmentException;
import org.teomant.appointment.security.domain.model.ActionNameEnum;
import org.teomant.appointment.security.domain.model.EntityNameEnum;
import org.teomant.appointment.security.service.RightChecker;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.repository.VoteRepository;
import org.teomant.appointment.vote.domain.service.VoteService;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final RightChecker rightChecker;

    @Override
    public Vote create(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public void delete(Vote vote, User currentUser) {
        Vote stored = voteRepository.findById(vote.getId());

        if (!rightChecker.checkCanPerform(EntityNameEnum.VOTE, ActionNameEnum.DELETE, stored.getUser(), currentUser)) {
            throw new AppointmentException("Can`t delete vote");
        }

        voteRepository.delete(vote);
    }
}
