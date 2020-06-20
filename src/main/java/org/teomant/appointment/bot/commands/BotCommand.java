package org.teomant.appointment.bot.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;

public interface BotCommand {

    Collection<? extends BotApiMethod> process(Update update);

}
