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
import org.teomant.appointment.security.persistance.repository.RoleEntityJpaRepository;
import org.teomant.appointment.user.persistance.repository.TelegramBotUserEntityJpaRepository;

import javax.annotation.PostConstruct;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

@Component
@Slf4j
@Lazy(false)
@RequiredArgsConstructor
public class BotStarter {

    private final TelegramBotUserEntityJpaRepository telegramBotUserEntityJpaRepository;
    private final RoleEntityJpaRepository roleEntityJpaRepository;
    @Value("${bot.proxy.username}")
    private String proxyUsername;
    @Value("${bot.proxy.password}")
    private String proxyPassword;
    @Value("${bot.proxy.host}")
    private String proxyHost;
    @Value("${bot.proxy.port}")
    private int proxyPort;
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.start:false}")
    private Boolean startBot;

    @PostConstruct
    public void init() {
        if (startBot) {
            ApiContextInitializer.init();

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(proxyUsername, proxyPassword.toCharArray());
                    }
                });

                botOptions.setProxyHost(proxyHost);
                botOptions.setProxyPort(proxyPort);
                botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

                TestBot bot = new TestBot(botName, botToken, botOptions, telegramBotUserEntityJpaRepository, roleEntityJpaRepository);
                telegramBotsApi.registerBot(bot);
            } catch (Exception e) {
                log.error("Error starting bot", e);
            }
        }
    }
}
