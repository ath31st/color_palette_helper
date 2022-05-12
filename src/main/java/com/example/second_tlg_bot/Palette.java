package com.example.second_tlg_bot;

import com.example.second_tlg_bot.util.HSLColor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
@Getter
@Setter
public class Palette {
    private String hexCode;
    private Color color;
    private Graphics graphics;
    private final BufferedImage bufferedImage = new BufferedImage(600, 100, BufferedImage.TYPE_INT_RGB);

    private void saveImageToFile(BufferedImage bufferedImage) {
        try {
            ImageIO.write(bufferedImage, "jpg", new File("src/main/resources/tmp1.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPalette() {
        graphics = bufferedImage.createGraphics();
        color = hex2Rgb(hexCode);
        graphics.setColor(color);
        graphics.fillRect(0, 0, 600, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 0, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyComplementaryMode() {
        createPalette();

        HSLColor hslColor = new HSLColor(color);
        color = hslColor.getComplementary();

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(300, 0, 300, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 300, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyAnalogousMode() {
        createPalette();

        HSLColor hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 30);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(200, 0, 200, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 200, 50);
        graphics.dispose();

        color = hslColor.adjustHue(hslColor.getHue() + 330);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(400, 0, 200, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 400, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyTriadicMode() {
        createPalette();

        HSLColor hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 120);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(200, 0, 200, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 200, 50);
        graphics.dispose();

        color = hslColor.adjustHue(hslColor.getHue() + 240);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(400, 0, 200, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 400, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyTetradicMode() {
        createPalette();

        HSLColor hslColor = new HSLColor(color);
        color = hslColor.adjustHue(hslColor.getHue() + 90);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(150, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 150, 50);
        graphics.dispose();

        color = hslColor.adjustHue(hslColor.getHue() + 180);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(300, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 300, 50);
        graphics.dispose();

        color = hslColor.adjustHue(hslColor.getHue() + 270);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(450, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 450, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyMonochromaticMode() {
        createPalette();

        HSLColor hslColor = new HSLColor(color);
        float luminance = hslColor.getLuminance();
        color = hslColor.adjustLuminance(luminance * 1.10f);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(150, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 150, 50);
        graphics.dispose();

        color = hslColor.adjustLuminance(luminance * 1.20f);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(300, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 300, 50);
        graphics.dispose();

        color = hslColor.adjustLuminance(luminance * 1.30f);

        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(450, 0, 150, 100);

        graphics.setFont(graphics.getFont().deriveFont(30f));
        hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 450, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }


    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }
}
