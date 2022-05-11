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
            try {
                // create file with config hexcode!
                // after that - menu
                execute(service.createMenuMessage(update));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        if (update.hasCallbackQuery()) {
            String callBachDate = update.getCallbackQuery().getData();
            switch (callBachDate) {
                case "COMPLEMENTARY":
                    try {
                        execute(service.createPhotoMessage(update));
                        execute(service.createMessage(update));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
//                case "MONOCHROMATIC":
//                    execute();
//                case "ANALOGOUS":
//                    execute();
//                case "TRIADIC":
//                    execute();
//                case "TETRADIC":
//                    execute();
            }
        }
    }
}
