package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.MedicalCaseImageDao;
import com.triple.o.labs.imageAnalizer.daos.StlDao;
import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import com.triple.o.labs.imageAnalizer.entities.Stl;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.ScannerImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.PersistenceException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ScannerImagesServiceImpl implements ScannerImagesService {

    @Autowired
    private MedicalCaseImageDao medicalCaseImageDao;

    @Autowired
    private StlDao stlDao;

    private byte[] mergeImage(BufferedImage upperImage, BufferedImage lowerImage) {
        int width;
        int cx1;
        int cx2;
        boolean lower;
        if (upperImage.getWidth() >= lowerImage.getWidth()) {
            width = upperImage.getWidth();
            cx1 = lowerImage.getWidth() / 2;
            cx2 = upperImage.getWidth() / 2;
            lower = false;
        } else {
            width = lowerImage.getWidth();
            cx1 = upperImage.getWidth() / 2;
            cx2 = lowerImage.getWidth() / 2;
            lower = true;
        }

        int height = upperImage.getHeight() + lowerImage.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(upperImage, null, lower ? cx2 - cx1 : 0, 0);
        g2.drawImage(lowerImage, null,  lower ? 0 : cx2 - cx1, upperImage.getHeight());
        g2.dispose();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(newImage, "png", bos );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    @Override
    public MedicalCaseImage saveMedicalCaseImage(Long idPatient, byte[] upperImage, byte[] lowerImage) {
        InputStream upperBase64 = new ByteArrayInputStream(upperImage);
        InputStream lowerBase64 = new ByteArrayInputStream(lowerImage);

        BufferedImage upper = null;
        BufferedImage lower = null;

        try {
            upper = ImageIO.read(upperBase64);
            lower = ImageIO.read(lowerBase64);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] imageMerged = mergeImage(upper, lower);

        MedicalCaseImage medicalCaseImage = new MedicalCaseImage();
        medicalCaseImage.setBase64image(imageMerged);
        return medicalCaseImageDao.save(medicalCaseImage);
    }

    @Override
    public Stl saveSTL(Long idPatient, byte[] stl) {
        Stl stlEntity = new Stl();
        stlEntity.setBase64image(stl);
        return stlDao.save(stlEntity);
    }
}
