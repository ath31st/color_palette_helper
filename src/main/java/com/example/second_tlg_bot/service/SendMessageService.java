package com.example.second_tlg_bot.service;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.stream.Collectors;

@Service
public class SendMessageService {
    private String startMessage = "Hello";
    String url = "http://img2.reactor.cc/pics/post/PaintrFiend-artist-Gawr-Gura-Hololive-7373460.jpeg";


    public SendMessage createStartMessage(Update update) {
        return createMessage(update, startMessage);
    }

    private SendMessage createMessage(Update update, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        return sendMessage;
    }

    public SendPhoto createPhotoMessage(Update update) {
        String hexCode = update.getMessage().getText();

        BufferedImage bufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        Color color = hex2Rgb(hexCode);
        graphics.setColor(color);
        graphics.fillRect(0, 0, 200, 200);
        graphics.setFont(graphics.getFont().deriveFont(30f));

        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex,50,50);
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

    public static float[] rgbToHsl(float[] rgb) {
        float r, g, b;
        r = rgb[0];
        g = rgb[1];
        b = rgb[2];
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));
        float avg = (max + min) / 2;
        float h = avg, s = avg, l = avg;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            if (max == r) {
                h = (g - b) / d + (g < b ? 6 : 0);
            } else if (max == g) {
                h = (b - r) / d + 2;
            } else if (max == b) {
                h = (r - g) / d + 4;
            }
            h /= 6;
        }

        return new float[] { h, s, l };
    }
}
