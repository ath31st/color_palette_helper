package com.example.second_tlg_bot;

public class App {
    public static void main(String[] args) {
        // 1. bot name; 2. bot token.
        TelegramBot telegramBot = new TelegramBot(args[0], args[1]);
        telegramBot.botConnect();
    }
}
