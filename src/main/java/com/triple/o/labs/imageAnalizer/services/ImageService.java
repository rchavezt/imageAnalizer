package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.enums.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image storeFile(MultipartFile file, ImageType imageType);
    Image getFile(Long id);
    Image saveMedicalCaseImage(Long idPatient, byte[] upper, byte[] lower);
}
