package bot.farm.color_palette_helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Palette {
  private static final int WIDTH = 300;
  private static final int HEIGHT = 100;
  private Color color;
  private Graphics graphics;
  private HSLColor hslColor;
  public BufferedImage bufferedImage;
  private final List<String> hexCodes = new ArrayList<>();
  private final HashMap<String, String> usersHexCodes = new HashMap<>();

  public void createPalette(String chatId, String hexCode) {
    usersHexCodes.put(chatId, hexCode);
    bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    graphics = bufferedImage.createGraphics();
    color = hexToRgb(hexCode);

    drawRectangle(0);
    drawTextOnGraphics(0);
  }

  public void applyComplementaryMode(String chatId) {
    initImageForEditing(chatId, 2);

    hslColor = new HSLColor(color);
    color = hslColor.getComplementary();

    drawRectangle(100);
    drawTextOnGraphics(100);

    graphics.dispose();
  }

  public void applyAnalogousMode(String chatId) {
    initImageForEditing(chatId, 3);

    hslColor = new HSLColor(color);
    color = hslColor.adjustHue(hslColor.getHue() + 30);

    drawRectangle(100);
    drawTextOnGraphics(100);

    color = hslColor.adjustHue(hslColor.getHue() + 330);

    drawRectangle(200);
    drawTextOnGraphics(200);
    graphics.dispose();
  }

  public void applyTriadicMode(String chatId) {
    initImageForEditing(chatId, 3);

    hslColor = new HSLColor(color);
    color = hslColor.adjustHue(hslColor.getHue() + 120);

    drawRectangle(100);
    drawTextOnGraphics(100);

    color = hslColor.adjustHue(hslColor.getHue() + 240);

    drawRectangle(200);
    drawTextOnGraphics(200);
    graphics.dispose();
  }

  public void applyTetradicMode(String chatId) {
    initImageForEditing(chatId, 4);

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
  }

  public void applyMonochromaticMode(String chatId) {
    initImageForEditing(chatId, 4);

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

  private void initImageForEditing(String chatId, int multiplier) {
    color = hexToRgb(usersHexCodes.get(chatId));

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

  public InputStream saveImageToInputStream() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  }

}
