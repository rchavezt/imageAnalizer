package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.awt.image.BufferedImage;

public interface ScannerImagesService {
    MedicalCaseImage saveMedicalCaseImage(Long idPatient, byte[] upper, byte[] lower);
}
