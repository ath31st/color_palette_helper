package com.example.second_tlg_bot;

import com.example.second_tlg_bot.service.FileService;
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
    @Autowired
    private FileService fileService;

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
            if (inputText.matches("^#(?:[0-9a-fA-F]{3}){1,2}$")) {
                try {
                    fileService.deleteOldFilesFromImagesDirectory();

                    palette.createPalette(chatId, inputText);

                    execute(service.createPhotoMessage(chatId));
                    execute(service.createMessage(chatId, palette.showResultHexCodes()));
                    execute(service.createMenuMessage(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    execute(service.createMessage(chatId, "Wrong hexcode. Please enter correct color hexcode"));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (update.hasCallbackQuery()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            String callBachDate = update.getCallbackQuery().getData();
            switch (callBachDate) {
                case "complementary":
                    palette.applyComplementaryMode(chatId);
                    executeCommands(chatId);
                    break;
                case "monochromatic":
                    palette.applyMonochromaticMode(chatId);
                    executeCommands(chatId);
                    break;
                case "analogous":
                    palette.applyAnalogousMode(chatId);
                    executeCommands(chatId);
                    break;
                case "triadic":
                    palette.applyTriadicMode(chatId);
                    executeCommands(chatId);
                    break;
                case "tetradic":
                    palette.applyTetradicMode(chatId);
                    executeCommands(chatId);
                    break;
            }
        }
    }

    private void executeCommands(String chatId) {
        try {
            execute(service.createPhotoMessage(chatId));
            execute(service.createMenuMessage(chatId));
            execute(service.createMessage(chatId,palette.showResultHexCodes()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
