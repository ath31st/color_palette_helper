package com.example.second_tlg_bot;

import com.example.second_tlg_bot.service.SendMessageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Setter
public class TelegramBot extends TelegramLongPollingBot {
    private String BOT_NAME;
    private String BOT_TOKEN;

    @Autowired
    private Palette palette;
    @Autowired
    private SendMessageService service;

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String inputText = update.getMessage().getText();
            // todo would make check on valid hexcode in inputText

            try {
                palette.createPalette(chatId,inputText);

                execute(service.createPhotoMessage(chatId));
                execute(service.createMessage(chatId, palette.toString()));
                execute(service.createMenuMessage(chatId));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        if (update.hasCallbackQuery()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            String callBachDate = update.getCallbackQuery().getData();
            switch (callBachDate) {
                case "complementary":
                  //  palette. // retrieve file and work with him
                    palette.applyComplementaryMode(chatId);
                    try {
                        execute(service.createPhotoMessage(chatId));
                        execute(service.createMenuMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "monochromatic":
                    palette.applyMonochromaticMode(chatId);
                    try {
                        execute(service.createPhotoMessage(chatId));
                        execute(service.createMenuMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "analogous":
                    palette.applyAnalogousMode(chatId);
                    try {
                        execute(service.createPhotoMessage(chatId));
                        execute(service.createMenuMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "triadic":
                    palette.applyTriadicMode(chatId);
                    try {
                        execute(service.createPhotoMessage(chatId));
                        execute(service.createMenuMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "tetradic":
                    palette.applyTetradicMode(chatId);
                    try {
                        execute(service.createPhotoMessage(chatId));
                        execute(service.createMenuMessage(chatId));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }
}
