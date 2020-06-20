package org.teomant.appointment.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.teomant.appointment.bot.commands.BotCommand;
import org.teomant.appointment.bot.service.BotService;
import org.teomant.appointment.user.domain.model.User;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor

public class MessageBotCommand implements BotCommand {

    private final BotService botService;

    @Override
    public Collection<SendMessage> process(Update update) {

        Message message = update.getMessage();

        User user = botService.getUser(message.getChat().getFirstName(), message.getChat().getUserName(), message.getChatId());

        SendMessage sendMessage = new SendMessage(message.getChatId(), "Hi, " + user.getUsername() + "\n"
                + "Send /appointment {appointment_number} to receive appointment information and options for vote");

        return Collections.singleton(sendMessage);
    }
}
