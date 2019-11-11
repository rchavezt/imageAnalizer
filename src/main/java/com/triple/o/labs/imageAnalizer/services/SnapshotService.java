package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Snapshot;
import org.springframework.web.multipart.MultipartFile;

public interface SnapshotService {
    Snapshot storeFile(MultipartFile file);
    Snapshot getFile(Long id);
}
