package org.teomant.appointment.bot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.teomant.appointment.appointment.domain.model.Appointment;
import org.teomant.appointment.appointment.domain.model.Option;
import org.teomant.appointment.bot.commands.BotCommand;
import org.teomant.appointment.bot.service.BotService;
import org.teomant.appointment.user.domain.model.User;
import org.teomant.appointment.vote.domain.model.Vote;
import org.teomant.appointment.vote.domain.model.VoteEnum;

import java.util.*;

import static org.teomant.appointment.vote.domain.model.VoteEnum.*;

@RequiredArgsConstructor

public class AppoinmentMessageBotCommand implements BotCommand {

    private final BotService botService;

    @Override
    public Collection<SendMessage> process(Update update) {

        Message message = update.getMessage();
        Long chatId = message.getChatId();

        List<SendMessage> result = new ArrayList<>();

        String appointmentIdString = StringUtils.substringBefore(StringUtils.substringAfter(message.getText(), "/appointment").trim(), " ");

        try {
            Long appointmentId = Long.valueOf(appointmentIdString);
            Appointment appointment = botService.getAppointment(appointmentId);

            User user = botService.getUser(message.getChat().getFirstName(), message.getChat().getUserName(), chatId);

            SendMessage appointmentMassega = new SendMessage();
            appointmentMassega.setChatId(chatId);
            appointmentMassega.setText(appointment.getComment() + "\n"
                    + "Latitude: " + appointment.getLatitude() + "\n"
                    + "Longitude: " + appointment.getLongitude() + "\n"
                    + "Vote till: " + appointment.getTill());

            result.add(appointmentMassega);

            appointment.getOptions().forEach(option -> {

                SendMessage optionMessage = new SendMessage();
                optionMessage.setChatId(chatId);
                optionMessage.setText(option.getComment() + "\n"
                        + "DateTime: " + option.getDateTime());

                InlineKeyboardButton totallyDisagreeButton = getInlineKeyboardButton(option, "1", TOTALLY_DISAGREE, user);
                InlineKeyboardButton disagreeButton = getInlineKeyboardButton(option, "2", DISAGREE, user);
                InlineKeyboardButton neutralButton = getInlineKeyboardButton(option, "3", NEUTRAL, user);
                InlineKeyboardButton agreeButton = getInlineKeyboardButton(option, "4", AGREE, user);
                InlineKeyboardButton totallyAgreeButton = getInlineKeyboardButton(option, "5", TOTALLY_AGREE, user);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(Collections.singletonList(Arrays.asList(totallyDisagreeButton,
                        disagreeButton,
                        neutralButton,
                        agreeButton,
                        totallyAgreeButton)));

                optionMessage.setReplyMarkup(inlineKeyboardMarkup);

                result.add(optionMessage);
            });

        } catch (NumberFormatException e) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Wrong appoinment number");

            result.add(sendMessage);
        }

        return result;
    }

    private InlineKeyboardButton getInlineKeyboardButton(Option option, String name, VoteEnum type, User user) {
        InlineKeyboardButton totallyAgreeButton = new InlineKeyboardButton();

        Optional<Vote> alreadyVotedForOption = option.getVotes().stream()
                .filter(vote -> vote.getType().equals(type) && vote.getUser() != null && vote.getUser().equals(user))
                .findAny();

        String finalText = name
                + String.format("(%d)", option.getVotes().stream()
                .filter(vote -> vote.getType().equals(type))
                .count())
                + (alreadyVotedForOption.isPresent() ? "!" : "");

        totallyAgreeButton.setText(finalText);

        JSONObject dataObject = new JSONObject();
        dataObject.put("optionId", option.getId());
        dataObject.put("type", type.name());

        alreadyVotedForOption.ifPresent(vote -> dataObject.put("voteId", vote.getId()));

        totallyAgreeButton.setCallbackData(dataObject.toString());
        return totallyAgreeButton;
    }
}
