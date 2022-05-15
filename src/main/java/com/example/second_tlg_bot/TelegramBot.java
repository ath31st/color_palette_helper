package com.example.second_tlg_bot;

import com.example.second_tlg_bot.service.ButtonService;
import com.example.second_tlg_bot.service.FileService;
import com.example.second_tlg_bot.service.SendMessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Getter
@Setter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final int RECONNECT_PAUSE = 10000;
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private FileService fileService = new FileService();
    private Palette palette = new Palette(fileService);
    private SendMessageService service = new SendMessageService(new ButtonService(),fileService);
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
            if (inputText.matches("^#(?:[\\da-fA-F]{3}){1,2}$")) {
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
                case "complementary" -> {
                    palette.applyComplementaryMode(chatId);
                    executeCommands(chatId);
                }
                case "monochromatic" -> {
                    palette.applyMonochromaticMode(chatId);
                    executeCommands(chatId);
                }
                case "analogous" -> {
                    palette.applyAnalogousMode(chatId);
                    executeCommands(chatId);
                }
                case "triadic" -> {
                    palette.applyTriadicMode(chatId);
                    executeCommands(chatId);
                }
                case "tetradic" -> {
                    palette.applyTetradicMode(chatId);
                    executeCommands(chatId);
                }
            }
        }
    }

    private void executeCommands(String chatId) {
        try {
            execute(service.createPhotoMessage(chatId));
            execute(service.createMenuMessage(chatId));
            execute(service.createMessage(chatId, palette.showResultHexCodes()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void botConnect() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}
