package org.teomant.appointment.user.domain.model;

import lombok.Data;

@Data
public class TelegramBotUser extends User {
    private String login;
    private Long chatId;
}
