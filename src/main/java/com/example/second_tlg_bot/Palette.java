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
    private final BufferedImage bufferedImage = new BufferedImage(400, 100, BufferedImage.TYPE_INT_RGB);

    private void saveImageToFile(BufferedImage bufferedImage) {
        try {
            ImageIO.write(bufferedImage, "jpg", new File("src/main/resources/tmp1.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPalette(String hexCode) {
        this.hexCode = hexCode;
        Graphics graphics = bufferedImage.createGraphics();
        color = hex2Rgb(hexCode);
        graphics.setColor(color);
        graphics.fillRect(0, 0, 400, 100);
        graphics.setFont(graphics.getFont().deriveFont(30f));

        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 0, 50);
        graphics.dispose();

        saveImageToFile(bufferedImage);
    }

    public void applyComplementaryMode() {
        HSLColor hslColor = new HSLColor(color);
        color = hslColor.getComplementary();

        Graphics graphics = bufferedImage.createGraphics();
        graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        graphics.fillRect(200, 0, 200, 100);
        graphics.setFont(graphics.getFont().deriveFont(30f));

        String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);
        graphics.setColor(Color.BLACK);
        graphics.drawString(hex, 200, 50);
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
