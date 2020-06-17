package org.teomant.appointment.user.persistance.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "telegram_users")
public class TelegramBotUserEntity extends UserEntity {

    @Column
    private String name;

    @Column
    private String login;

    @Column(name = "chat_id")
    private Long chatId;
}
