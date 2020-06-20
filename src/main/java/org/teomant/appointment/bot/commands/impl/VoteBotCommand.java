package org.teomant.appointment.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.teomant.appointment.bot.commands.BotCommand;
import org.teomant.appointment.bot.service.BotService;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.model.VoteEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor

public class VoteBotCommand implements BotCommand {

    private final BotService botService;

    @Override
    public Collection<BotApiMethod> process(Update update) {

        List<BotApiMethod> result = new ArrayList<>();

        CallbackQuery callbackQuery = update.getCallbackQuery();

        Message message = callbackQuery.getMessage();
        Chat chat = message.getChat();

        User user = botService.getUser(chat.getFirstName(), chat.getUserName(), chat.getId());

        JSONObject jsonObject = new JSONObject(callbackQuery.getData());
        Long optionId = jsonObject.getLong("optionId");
        VoteEnum type = VoteEnum.valueOf(jsonObject.getString("type"));


        if (!jsonObject.has("voteId")) {
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setType(type);
            vote.setOptionId(optionId);
            vote.setComment("Vote from telegram");

            Vote saved = botService.saveVote(vote);

            result.add(new SendMessage(chat.getId(), "Vote counted"));

            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setChatId(chat.getId());
            editMessageReplyMarkup.setMessageId(message.getMessageId());

            InlineKeyboardMarkup replyMarkup = message.getReplyMarkup();
            replyMarkup.getKeyboard().forEach(row -> {
                row.forEach(button -> {
                    if (button.getCallbackData().equals(callbackQuery.getData())) {
                        button.setText(button.getText() + "!");
                        JSONObject object = new JSONObject(button.getCallbackData());
                        object.put("voteId", saved.getId());
                        button.setCallbackData(object.toString());
                    }
                });
            });

            editMessageReplyMarkup.setReplyMarkup(replyMarkup);

            result.add(editMessageReplyMarkup);
        } else {
            Vote vote = new Vote();
            vote.setId(jsonObject.getLong("voteId"));

            botService.removeVote(vote);

            result.add(new SendMessage(chat.getId(), "Vote removed"));

            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
            editMessageReplyMarkup.setChatId(chat.getId());
            editMessageReplyMarkup.setMessageId(message.getMessageId());

            InlineKeyboardMarkup replyMarkup = message.getReplyMarkup();
            replyMarkup.getKeyboard().forEach(row -> {
                row.forEach(button -> {
                    if (button.getCallbackData().equals(callbackQuery.getData())) {
                        button.setText(button.getText().replace("!", ""));
                        JSONObject object = new JSONObject(button.getCallbackData());
                        object.remove("voteId");
                        button.setCallbackData(object.toString());
                    }
                });
            });
            editMessageReplyMarkup.setReplyMarkup(replyMarkup);

            result.add(editMessageReplyMarkup);
        }

        return result;
    }
}
