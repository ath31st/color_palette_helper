package com.example.second_tlg_bot.config;

import com.example.second_tlg_bot.TelegramBot;
import com.example.second_tlg_bot.service.SendMessageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String BOT_NAME;
    private String BOT_TOKEN;

    @Bean
    public TelegramBot telegramBot() {

        TelegramBot telegramBot = new TelegramBot();
        telegramBot.setBOT_NAME(BOT_NAME);
        telegramBot.setBOT_TOKEN(BOT_TOKEN);

        return telegramBot;
    }
    @Bean
    public SendMessageService sendMessageService() {
        return new SendMessageService();
    }
}
