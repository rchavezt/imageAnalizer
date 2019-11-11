package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.SnapshotDao;
import com.triple.o.labs.imageAnalizer.entities.Snapshot;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.SnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    @Autowired
    private SnapshotDao snapshotDao;

    @Override
    public Snapshot storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            Snapshot snapshot = new Snapshot();

            snapshot.setFileName(fileName);
            snapshot.setBase64file(file.getBytes());
            snapshot.setFileType(file.getContentType());

            return snapshotDao.save(snapshot);
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file " + fileName + ". Please try again", ex);
        }
    }

    @Override
    public Snapshot getFile(Long id) {
        return snapshotDao.findById(id).get();
    }
}
