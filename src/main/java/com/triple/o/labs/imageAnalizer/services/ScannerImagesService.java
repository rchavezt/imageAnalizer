package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;

public interface ScannerImagesService {
    MedicalCaseImage saveMedicalCaseImage(Long idPatient, byte[] upper, byte[] lower);
}
