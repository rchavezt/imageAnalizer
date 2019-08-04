package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.ScannerImagesService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ScannerImagesServiceImpl implements ScannerImagesService {
    @Override
    public byte[] mergeImage(BufferedImage upperImage, BufferedImage lowerImage, User patient, String fileName) {
        File path = new File("../images");

        int width = upperImage.getWidth();
        int height = upperImage.getHeight() + lowerImage.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(upperImage, null, 0, 0);
        g2.drawImage(lowerImage, null,  0, upperImage.getHeight());
        g2.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(newImage, "png", new File(path, fileName));
            ImageIO.write(newImage, "png", bos );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();

    }
}
