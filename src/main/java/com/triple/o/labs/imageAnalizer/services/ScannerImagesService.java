package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.User;

import java.awt.image.BufferedImage;

public interface ScannerImagesService {
    byte[] mergeImage(BufferedImage upperImage, BufferedImage lowerImage, User patient, String fileName);
}
