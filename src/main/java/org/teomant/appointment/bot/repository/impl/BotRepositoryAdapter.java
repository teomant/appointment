package org.teomant.appointment.bot.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.repository.AppointmentRepository;
import org.teomant.appointment.bot.repository.BotRepository;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.user.persistance.mapper.UserMapper;
import org.teomant.appointment.user.persistance.model.TelegramBotUserEntity;
import org.teomant.appointment.user.persistance.repository.TelegramBotUserEntityJpaRepository;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.repository.VoteRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@Repository
@RequiredArgsConstructor
public class BotRepositoryAdapter implements BotRepository {

    private final TelegramBotUserEntityJpaRepository telegramBotUserEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;
    private final AppointmentRepository appointmentRepository;
    private final VoteRepository voteRepository;
    private final UserMapper userMapper = new UserMapper();

    @Override
    @Transactional
    public User findUserByChatId(Long chatId) {
        TelegramBotUserEntity byChatId = telegramBotUserEntityJpaRepository.findByChatId(chatId);
        return byChatId != null ? userMapper.toModel(byChatId) : null;
    }

    @Override
    public User saveUser(String firstName, String login, Long chatId) {
        TelegramBotUserEntity telegramBotUserEntity = new TelegramBotUserEntity();
        telegramBotUserEntity.setLogin(login);
        telegramBotUserEntity.setChatId(chatId);
        telegramBotUserEntity.setName(firstName);
        telegramBotUserEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName("ROLE_USER")));

        return userMapper.toModel(telegramBotUserEntityJpaRepository.saveAndFlush(telegramBotUserEntity));
    }

    @Override
    public Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.get(appointmentId);
    }

    @Override
    public Vote saveVote(Vote newVote) {
        return voteRepository.save(newVote);
    }

    @Override
    public void removeVote(Vote vote) {
        voteRepository.delete(vote);
    }
}
