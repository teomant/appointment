package org.teomant.appointment.bot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.bot.repository.BotRepository;
import org.teomant.appointment.bot.service.BotService;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final BotRepository botRepository;

    @Override
    public User getUser(String firstName, String login, Long chatId) {
        User botUser = botRepository.findUserByChatId(chatId);

        if (botUser == null) {
            botUser = botRepository.saveUser(firstName, login, chatId);
        }

        return botUser;
    }

    @Override
    @Transactional
    public Appointment getAppointment(Long appointmentId) {
        return botRepository.getAppointment(appointmentId);
    }

    @Override
    @Transactional
    public Vote saveVote(Vote newVote) {
        return botRepository.saveVote(newVote);
    }

    @Override
    @Transactional
    public void removeVote(Vote vote) {
        botRepository.removeVote(vote);
    }
}
