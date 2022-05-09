package com.example.second_tlg_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class SecondTlgBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondTlgBotApplication.class, args);
    }

}
