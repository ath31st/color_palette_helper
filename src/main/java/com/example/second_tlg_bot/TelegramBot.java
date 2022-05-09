package com.example.second_tlg_bot;

import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Setter
public class TelegramBot extends TelegramLongPollingBot {
    private String BOT_NAME;
    private String BOT_TOKEN;

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
        if (update.getMessage() != null && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            try {
                execute(new SendMessage(chatId, "Hi"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
