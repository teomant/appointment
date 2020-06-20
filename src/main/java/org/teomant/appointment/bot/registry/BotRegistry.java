package org.teomant.appointment.bot.registry;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class BotRegistry {
    private Map<String, TelegramLongPollingBot> bots = new HashMap<>();
}
