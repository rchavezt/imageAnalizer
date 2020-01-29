package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image storeFile(MultipartFile file, ImageType imageType, MedicalCase medicalCase);
    Image getFile(Long id);
    void updateStl(Image stl, MedicalCase medicalCase);
    Image saveMedicalCaseImage(Long idPatient, byte[] upper, byte[] lower);
    void deleteExtras(List<Image> extras);
}
