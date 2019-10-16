package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Stl;
import org.springframework.web.multipart.MultipartFile;

public interface StlService {
    Stl storeFile(MultipartFile file);
    Stl getFile(Long id);
}
