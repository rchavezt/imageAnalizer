package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import com.triple.o.labs.imageAnalizer.entities.Stl;

public interface ScannerImagesService {
    MedicalCaseImage saveMedicalCaseImage(Long idPatient, byte[] upper, byte[] lower);
    Stl saveSTL(Long idPatient, byte[] stl);
}
