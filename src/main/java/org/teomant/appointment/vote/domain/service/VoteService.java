package org.teomant.appointment.vote.domain.service;

import org.teomant.appointment.user.domain.model.SiteUser;
import org.teomant.appointment.vote.domain.model.Vote;

public interface VoteService {

    Vote create(Vote vote);

    void delete(Vote vote, SiteUser currentSiteUser);

}
