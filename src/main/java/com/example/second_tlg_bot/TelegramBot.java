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

import static com.example.second_tlg_bot.Constants.*;

@Getter
@Setter
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final int RECONNECT_PAUSE = 10000;
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private FileService fileService = new FileService();
    private Palette palette = new Palette(fileService);
    private SendMessageService service = new SendMessageService(new ButtonService(), fileService);

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
            if (inputText.equals("/start")) {
                sendTextMessage(chatId, START);
            } else if (inputText.equals("/help")) {
                sendTextMessage(chatId, HELP);
            } else if (inputText.matches(HEXCODE_COLOR_REGEX)) {
                fileService.deleteOldFilesFromImagesDirectory();

                palette.createPalette(chatId, inputText);
                executeCommands(chatId);
            } else {
                sendTextMessage(chatId, WRONG_HEX);
            }
        }
        if (update.hasCallbackQuery()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            String callBachDate = update.getCallbackQuery().getData();

            switch (callBachDate) {
                case COMPLEMENTARY -> palette.applyComplementaryMode(chatId);
                case MONOCHROMATIC -> palette.applyMonochromaticMode(chatId);
                case ANALOGOUS -> palette.applyAnalogousMode(chatId);
                case TRIADIC -> palette.applyTriadicMode(chatId);
                case TETRADIC -> palette.applyTetradicMode(chatId);
            }
            executeCommands(chatId);
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

    private void sendTextMessage(String chatId, String text) {
        try {
            execute(service.createMessage(chatId, text));
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
