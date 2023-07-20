package bot.farm.color_palette_helper;

public class App {
  public static void main(String[] args) {
    // 1. bot name; 2. bot token.
    TelegramBot telegramBot = new TelegramBot(args[0], args[1]);
    telegramBot.botConnect();
  }
}
