package com.example.second_tlg_bot;

import com.example.second_tlg_bot.service.FileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Palette {
    private final int WIDTH = 300;
    private final int HEIGHT = 100;
    private Color color;
    private Graphics graphics;
    private HSLColor hslColor;
    private BufferedImage bufferedImage;
    private final FileService fileService;
    private final List<String> hexCodes = new ArrayList<>();

    public void createPalette(String nameFileAsChatId, String hexCode) {
        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.createGraphics();
        color = hexToRgb(hexCode);

        drawRectangle(0);
        drawTextOnGraphics(0);
        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyComplementaryMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId, 2);

        hslColor = new HSLColor(color);
        color = hslColor.getComplementary();

        drawRectangle(100);
        drawTextOnGraphics(100);

        graphics.dispose();
        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyAnalogousMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId, 3);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 30);

        drawRectangle(100);
        drawTextOnGraphics(100);

        color = hslColor.adjustHue(hslColor.getHue() + 330);

        drawRectangle(200);
        drawTextOnGraphics(200);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyTriadicMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId, 3);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 120);

        drawRectangle(100);
        drawTextOnGraphics(100);

        color = hslColor.adjustHue(hslColor.getHue() + 240);

        drawRectangle(200);
        drawTextOnGraphics(200);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyTetradicMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId, 4);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 90);

        drawRectangle(100);
        drawTextOnGraphics(100);

        color = hslColor.adjustHue(hslColor.getHue() + 180);

        drawRectangle(200);
        drawTextOnGraphics(200);

        color = hslColor.adjustHue(hslColor.getHue() + 270);

        drawRectangle(300);
        drawTextOnGraphics(300);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyMonochromaticMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId, 4);

        //todo fix problem with luminance (out of range)

        hslColor = new HSLColor(color);
        float luminance = hslColor.getLuminance();
        if (luminance < 90)
            color = hslColor.adjustLuminance(luminance * 1.10f);

        drawRectangle(100);
        drawTextOnGraphics(100);

        if (luminance < 80)
            color = hslColor.adjustLuminance(luminance * 1.20f);

        drawRectangle(200);
        drawTextOnGraphics(200);

        if (luminance < 70)
            color = hslColor.adjustLuminance(luminance * 1.30f);

        drawRectangle(300);
        drawTextOnGraphics(300);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }


    public static Color hexToRgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private void drawRectangle(int yPoint) {
        graphics.setColor(color);
        graphics.fillRect(0, yPoint, WIDTH, HEIGHT);
    }

    private void drawTextOnGraphics(int yPoint) {
        ColorUtils colorUtils = new ColorUtils();

        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        String colorName = colorUtils.getColorNameFromColor(color);
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex + " " + colorName, 5, yPoint + 90);

        hexCodes.add(hex);
    }

    private void initImageForEditing(String nameFileAsChatId, int multiplier) {
        bufferedImage = fileService.readImageFromFile(nameFileAsChatId);
        color = new Color(bufferedImage.getRGB(0, 0));

        bufferedImage = new BufferedImage(WIDTH, HEIGHT * multiplier, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.createGraphics();
        drawRectangle(0);
        drawTextOnGraphics(0);
    }

    public String showResultHexCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : hexCodes) {
            stringBuilder
                    .append(s)
                    .append(" ");
        }
        hexCodes.clear();
        return stringBuilder.toString();
    }
}
