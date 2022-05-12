package com.example.second_tlg_bot.service;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileService {

    public void saveImageToFile(BufferedImage bufferedImage, String fileName) {
        try {
            ImageIO.write(bufferedImage, "jpg", new File("src/main/resources/images/" + fileName + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage readImageFromFile(String fileName) {
        try {
            return ImageIO.read(new File("src/main/resources/images/" + fileName + ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImageFile(String fileName) {
        try {
            Files.delete(Path.of("src/main/resources/images/" + fileName + ".jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
