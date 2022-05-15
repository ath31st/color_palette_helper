package com.example.second_tlg_bot.service;

import lombok.RequiredArgsConstructor;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FileService {
    private final int LIMIT_FILES_IN_DIRECTORY = 10;
    public final String FORMAT_FILE = ".png";
    public final String PATH_IMAGES_DIRECTORY = "src/main/images/";
    private final TreeMap<Long, String> tableImages = new TreeMap<>();

    public void saveImageToFile(BufferedImage bufferedImage, String fileName) {
        try {
            ImageIO.write(bufferedImage, "png", new File(PATH_IMAGES_DIRECTORY + fileName + FORMAT_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage readImageFromFile(String fileName) {
        try {
            return ImageIO.read(new File(PATH_IMAGES_DIRECTORY + fileName + FORMAT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteImageFile(String fileName) {
        try {
            Files.delete(Path.of(PATH_IMAGES_DIRECTORY + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOldFilesFromImagesDirectory() {
        updateTableImages();

        if (tableImages.size() > LIMIT_FILES_IN_DIRECTORY) {
            List<String> values = tableImages.values().stream().limit(5).collect(Collectors.toList());
            values.forEach(this::deleteImageFile);
        }
    }

    private void updateTableImages() {
        try (Stream<Path> paths = Files.walk(Paths.get(PATH_IMAGES_DIRECTORY))) {
            List<Path> listPaths = paths.filter(Files::isRegularFile).collect(Collectors.toList());

            for (Path p : listPaths) {
                BasicFileAttributes attributes = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
                tableImages.put(attributes.creationTime().toMillis(), p.getFileName().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
