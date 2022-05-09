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
                execute(service.createStartMessage(update));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
