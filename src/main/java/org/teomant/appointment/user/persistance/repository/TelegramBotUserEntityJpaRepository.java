package org.teomant.appointment.user.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.teomant.appointment.user.persistance.model.TelegramBotUserEntity;

@Repository
public interface TelegramBotUserEntityJpaRepository extends JpaRepository<TelegramBotUserEntity, Long> {

    TelegramBotUserEntity findByChatId(Long chatId);
}
