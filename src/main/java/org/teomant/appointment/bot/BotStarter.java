package org.teomant.appointment.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.teomant.appointment.bot.registry.BotRegistry;
import org.teomant.appointment.bot.service.BotService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@Lazy(false)
@RequiredArgsConstructor
public class BotStarter {

    private final BotService botService;
    private final BotRegistry botRegistry;

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            TestBot bot = new TestBot(botName, botToken, botOptions, botService);
            telegramBotsApi.registerBot(bot);
            botRegistry.getBots().put("telegram", bot);
        } catch (Exception e) {
            log.error("Error starting bot", e);
        }
    }
}
