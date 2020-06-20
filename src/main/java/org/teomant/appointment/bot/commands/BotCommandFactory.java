package org.teomant.appointment.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.teomant.appointment.bot.commands.impl.AppoinmentMessageBotCommand;
import org.teomant.appointment.bot.commands.impl.MessageBotCommand;
import org.teomant.appointment.bot.commands.impl.VoteBotCommand;
import org.teomant.appointment.bot.service.BotService;

@RequiredArgsConstructor
public class BotCommandFactory {

    private final BotService botService;

    public BotCommand getCommand(Update update) {

        if (update.hasMessage()) {
            if (update.getMessage().getText().startsWith("/appointment")) {
                return new AppoinmentMessageBotCommand(botService);
            } else {
                return new MessageBotCommand(botService);
            }
        } else if (update.hasCallbackQuery()) {
            return new VoteBotCommand(botService);
        } else {
            return null;
        }
    }

}
