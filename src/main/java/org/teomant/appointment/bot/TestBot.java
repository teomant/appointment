package org.teomant.appointment.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.persistance.model.TelegramBotUserEntity;
import org.teomant.appointment.user.persistance.repository.TelegramBotUserEntityJpaRepository;

import java.util.Collections;

@Getter
@Setter
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final TelegramBotUserEntityJpaRepository telegramBotUserEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;

    public TestBot(String botUsername, String botToken, DefaultBotOptions options,
                   TelegramBotUserEntityJpaRepository telegramBotUserEntityJpaRepository,
                   RoleEntityJpaRepository roleEntityJpaRepository) {
        super(options);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.telegramBotUserEntityJpaRepository = telegramBotUserEntityJpaRepository;
        this.roleEntityJpaRepository = roleEntityJpaRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();

        String message = "";

        TelegramBotUserEntity user = telegramBotUserEntityJpaRepository.findByChatId(chatId);
        if (user == null) {

            //TODO MOVE THIS ABOMINATION TO ADAPTER
            TelegramBotUserEntity telegramBotUserEntity = new TelegramBotUserEntity();
            telegramBotUserEntity.setLogin(update.getMessage().getChat().getUserName());
            telegramBotUserEntity.setChatId(chatId);
            telegramBotUserEntity.setName(update.getMessage().getChat().getFirstName());
            telegramBotUserEntity.setRoles(Collections.singleton(roleEntityJpaRepository.findByName("ROLE_USER")));

            TelegramBotUserEntity saved = telegramBotUserEntityJpaRepository.save(telegramBotUserEntity);

            message = "NOW I KNOW YOU " + saved.getLogin();
        } else {
            message = "I KNOW YOU " + user.getLogin();
        }
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Exception: " + e.toString());
        }
    }
}
