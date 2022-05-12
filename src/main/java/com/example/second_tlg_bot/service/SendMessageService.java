package com.example.second_tlg_bot.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;

@Service
public class SendMessageService {
    @Autowired
    private ButtonService buttonService;

    public SendMessage createMenuMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Choose the type of color combination");
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
//    public EditMessageMedia createEditMediaMessage(Update update) {
//        EditMessageMedia editMessageMedia = new EditMessageMedia();
//        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
//        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
//        editMessageMedia.setChatId(chatId);
//        editMessageMedia.setMessageId(messageId);
//        editMessageMedia.setMedia(new InputMediaPhoto().setMedia());
//        return editMessageMedia;
//    }

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
        sendPhoto.setPhoto(new InputFile(new File("src/main/resources/tmp1.jpg"), "tmp1"));
        return sendPhoto;
    }
}
