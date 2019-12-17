package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.ImageDao;
import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.enums.ImageType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public Image storeFile(MultipartFile file, ImageType imageType, MedicalCase medicalCase) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            Image image = new Image();

            image.setFileName(fileName);
            if (medicalCase != null) {
                image.setMedicalCase(medicalCase);
            }
            image.setBase64file(file.getBytes());
            image.setFileType(file.getContentType());
            image.setImageType(imageType);

            return imageDao.save(image);
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file " + fileName + ". Please try again", ex);
        }
    }

    @Override
    public Image getFile(Long id) {
        return imageDao.findById(id).get();
    }

    @Override
    public void updateStl(Image stl, MedicalCase medicalCase) {
        stl.setMedicalCase(medicalCase);
        imageDao.save(stl);
    }

    @Override
    public Image saveMedicalCaseImage(Long idPatient, byte[] upperImage, byte[] lowerImage) {
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

        Image medicalCaseImage = new Image();

        medicalCaseImage.setFileName(idPatient + new Date().getTime() + ".base64");
        medicalCaseImage.setBase64file(imageMerged);
        return imageDao.save(medicalCaseImage);
    }

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
}
