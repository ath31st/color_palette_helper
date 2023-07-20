package bot.farm.color_palette_helper;

import static bot.farm.color_palette_helper.Constants.*;

import bot.farm.color_palette_helper.service.ButtonService;
import bot.farm.color_palette_helper.service.SendMessageService;
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
  private static long COUNT = 0;
  private final int RECONNECT_PAUSE = 10000;
  private final String BOT_NAME;
  private final String BOT_TOKEN;


  private Palette palette = new Palette();
  private SendMessageService service = new SendMessageService(new ButtonService(), palette);

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
    COUNT++;
    System.out.println("new requests: " + COUNT);

    if (update.hasMessage() && update.getMessage().hasText()) {
      String chatId = String.valueOf(update.getMessage().getChatId());
      String inputText = update.getMessage().getText();

      if (inputText.equals("/start")) {
        sendTextMessage(chatId, START);
      } else if (inputText.equals("/help")) {
        sendTextMessage(chatId, HELP);
      } else if (inputText.matches(HEX_CODE_COLOR_REGEX)) {
        palette.createPalette(chatId, inputText);
        executeCommands(chatId);
      } else {
        sendTextMessage(chatId, WRONG_HEX);
      }
    }
    if (update.hasCallbackQuery()) {
      String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
      String callBackDate = update.getCallbackQuery().getData();

      switch (callBackDate) {
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
