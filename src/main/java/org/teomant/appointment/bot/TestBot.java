package org.teomant.appointment.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.teomant.appointment.bot.commands.BotCommand;
import org.teomant.appointment.bot.commands.BotCommandFactory;
import org.teomant.appointment.bot.service.BotService;
import org.teomant.appointment.notification.domain.model.Notification;
import org.teomant.appointment.user.domain.model.TelegramBotUser;

@Getter
@Setter
@Slf4j
public class TestBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final BotService botService;

    public TestBot(String botUsername, String botToken, DefaultBotOptions options, BotService botService) {
        super(options);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botService = botService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        BotCommandFactory factory = new BotCommandFactory(botService);
        BotCommand command = factory.getCommand(update);

        try {
            for (BotApiMethod botApiMethod : command.process(update)) {
                execute(botApiMethod);
            }
        } catch (TelegramApiException e) {
            log.error("Exception: " + e.toString());
        }
    }

    public void sendNotification(Notification notification, TelegramBotUser user) {
        try {
            execute(new SendMessage(user.getChatId(), notification.getComment()
                    + String.format(" (for appointment %d)", notification.getAppointment().getId())));
        } catch (TelegramApiException e) {
            log.error("Exception: " + e.toString());
        }
    }
}
