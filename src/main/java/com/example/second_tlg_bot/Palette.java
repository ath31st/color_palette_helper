package com.example.second_tlg_bot;

import com.example.second_tlg_bot.service.FileService;
import com.example.second_tlg_bot.util.HSLColor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.awt.image.BufferedImage;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class Palette {
    private Color color;
    private Graphics graphics;
    private HSLColor hslColor;
    private BufferedImage bufferedImage;
    @Autowired
    private final FileService fileService;

    public void createPalette(String nameFileAsChatId, String hexCode) {
        bufferedImage = new BufferedImage(600, 100, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.createGraphics();
        Color color = hexToRgb(hexCode);

        drawRectangle600x100(graphics, color);
        drawTextOnGraphics(graphics, color, 0);
        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyComplementaryMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId);

        hslColor = new HSLColor(color);
        color = hslColor.getComplementary();

        drawRectangle300x100(graphics, color, 300);
        drawTextOnGraphics(graphics, color, 300);

        graphics.dispose();
        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyAnalogousMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 30);

        drawRectangle200x100(graphics, color, 200);
        drawTextOnGraphics(graphics, color, 200);

        color = hslColor.adjustHue(hslColor.getHue() + 330);

        drawRectangle200x100(graphics, color, 400);
        drawTextOnGraphics(graphics, color, 400);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyTriadicMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 120);

        drawRectangle200x100(graphics, color, 200);
        drawTextOnGraphics(graphics, color, 200);

        color = hslColor.adjustHue(hslColor.getHue() + 240);

        drawRectangle200x100(graphics, color, 400);
        drawTextOnGraphics(graphics, color, 400);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyTetradicMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId);

        hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 90);

        drawRectangle150x100(graphics, color, 150);
        drawTextOnGraphics(graphics, color, 150);

        color = hslColor.adjustHue(hslColor.getHue() + 180);

        drawRectangle150x100(graphics, color, 300);
        drawTextOnGraphics(graphics, color, 300);

        color = hslColor.adjustHue(hslColor.getHue() + 270);

        drawRectangle150x100(graphics, color, 450);
        drawTextOnGraphics(graphics, color, 450);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }

    public void applyMonochromaticMode(String nameFileAsChatId) {
        initImageForEditing(nameFileAsChatId);

        hslColor = new HSLColor(color);
        float luminance = hslColor.getLuminance();
        color = hslColor.adjustLuminance(luminance * 1.10f);

        drawRectangle150x100(graphics, color, 150);
        drawTextOnGraphics(graphics, color, 150);

        color = hslColor.adjustLuminance(luminance * 1.20f);

        drawRectangle150x100(graphics, color, 300);
        drawTextOnGraphics(graphics, color, 300);

        color = hslColor.adjustLuminance(luminance * 1.30f);

        drawRectangle150x100(graphics, color, 450);
        drawTextOnGraphics(graphics, color, 450);
        graphics.dispose();

        fileService.saveImageToFile(bufferedImage, nameFileAsChatId);
    }


    public static Color hexToRgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private void drawRectangle600x100(Graphics graphics, Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, 600, 100);
    }

    private void drawRectangle300x100(Graphics graphics, Color color, int xPoint) {
        graphics.setColor(color);
        graphics.fillRect(xPoint, 0, 300, 100);
    }

    private void drawRectangle200x100(Graphics graphics, Color color, int xPoint) {
        graphics.setColor(color);
        graphics.fillRect(xPoint, 0, 200, 100);
    }

    private void drawRectangle150x100(Graphics graphics, Color color, int xPoint) {
        graphics.setColor(color);
        graphics.fillRect(xPoint, 0, 150, 100);
    }

    private void drawTextOnGraphics(Graphics graphics, Color color, int xPoint) {
        graphics.setFont(graphics.getFont().deriveFont(25f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, xPoint + 10, 90);
    }

    private void initImageForEditing(String nameFileAsChatId) {
        bufferedImage = fileService.readImageFromFile(nameFileAsChatId);

        graphics = bufferedImage.createGraphics();
        color = new Color(bufferedImage.getRGB(0, 0));
        drawRectangle600x100(graphics, color);
        drawTextOnGraphics(graphics, color, 0);
    }
}
