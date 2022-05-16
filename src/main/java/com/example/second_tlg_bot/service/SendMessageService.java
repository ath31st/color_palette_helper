package com.example.second_tlg_bot.service;

import com.example.second_tlg_bot.Palette;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.example.second_tlg_bot.Constants.MENU;

@Getter
@Setter
@RequiredArgsConstructor
public class SendMessageService {
    private final ButtonService buttonService;
    private final Palette palette;

    public SendMessage createMenuMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(MENU);
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public EditMessageText createEditMessage(Update update, String editMessage) {
        EditMessageText editMessageText = new EditMessageText();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(editMessage);
        return editMessageText;
    }

    public SendMessage createMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendPhoto createPhotoMessage(String chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(palette.saveImageToInputStream(),chatId));
        return sendPhoto;
    }
}
