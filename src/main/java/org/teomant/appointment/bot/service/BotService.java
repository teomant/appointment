package org.teomant.appointment.bot.service;

import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;

public interface BotService {

    User getUser(String firstName, String login, Long chatId);

    Appointment getAppointment(Long appointmentId);

    Vote saveVote(Vote newVote);

    void removeVote(Vote vote);
}
