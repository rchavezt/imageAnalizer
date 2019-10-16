package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.StlDao;
import com.triple.o.labs.imageAnalizer.entities.Stl;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.StlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StlServiceImpl implements StlService {

    @Autowired
    private StlDao stlDao;

    @Override
    public Stl storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            Stl stl = new Stl();

            stl.setFileName(fileName);
            stl.setBase64file(file.getBytes());
            stl.setFileType(file.getContentType());

            return stlDao.save(stl);
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file " + fileName + ". Please try again", ex);
        }
    }

    @Override
    public Stl getFile(Long id) {
        return stlDao.findById(id).get();
    }
}
