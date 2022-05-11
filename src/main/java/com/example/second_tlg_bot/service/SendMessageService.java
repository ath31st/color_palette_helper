package com.example.second_tlg_bot.service;

import com.example.second_tlg_bot.util.HSLColor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class SendMessageService {
    @Autowired
    private ButtonService buttonService;
    private String hexcode1;
    private String hexcode2;

    public SendMessage createMenuMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText("Select the desired palette: ");
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.setInlineKeyMarkup(buttonService.createInlineButton());
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public EditMessageText createEditMessage(Update update, String editMessage) {


        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setMedia(new InputMediaPhoto().setMedia());


        EditMessageText editMessageText = new EditMessageText();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(editMessage);
        return editMessageText;
    }

    public SendMessage createMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(hexcode1 + " " + hexcode2);
        return sendMessage;
    }

    public SendPhoto createPhotoMessage(Update update) {
        String hexCode = update.getMessage().getText();

        BufferedImage bufferedImage = new BufferedImage(400, 100, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        Color color = hex2Rgb(hexCode);
        graphics.setColor(color);
        graphics.fillRect(0, 0, 200, 100);
        graphics.setFont(graphics.getFont().deriveFont(30f));

        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        hexcode1 = hex;
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 0, 50);
        graphics.dispose();

        HSLColor hslColor = new HSLColor(color);
        color = hslColor.getComplementary();

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(200, 0, 200, 100);
        graphics.setFont(graphics.getFont().deriveFont(30f));

        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        hexcode2 = hex;
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 200, 50);
        graphics.dispose();

        try {
            ImageIO.write(bufferedImage, "jpg", new File("src/main/resources/tmp1.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(update.getMessage().getChatId()));
        //    sendPhoto.setPhoto(new InputFile().setMedia("src/main/resources/tmp1.jpg"));
        sendPhoto.setPhoto(new InputFile(new File("src/main/resources/tmp1.jpg"), "tmp1"));
        return sendPhoto;
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
}
