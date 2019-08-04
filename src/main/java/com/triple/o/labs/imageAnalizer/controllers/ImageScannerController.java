package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.dtos.image.ImageRequestDto;
import com.triple.o.labs.imageAnalizer.dtos.image.ImageResponseDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.ScannerImagesService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

@RestController
@RequestMapping(value = "/image")
public class ImageScannerController {

    @Autowired
    private ScannerImagesService scannerImagesService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_SCAN_IMAGES_UPLOAD")
    @RequestMapping(value = "/getFinal/patient/{idPatient}", method = RequestMethod.POST, produces = "application/json")
    public ImageResponseDto getFinalImage(@PathVariable Long idPatient, @RequestBody ImageRequestDto imageRequestDto) {
        User user = userService.getUser(idPatient);

        InputStream upperBase64 = new ByteArrayInputStream(imageRequestDto.getUpper());
        InputStream lowerBase64 = new ByteArrayInputStream(imageRequestDto.getLower());

        BufferedImage upper = null;
        BufferedImage lower = null;

        try {
            upper = ImageIO.read(upperBase64);
            lower = ImageIO.read(lowerBase64);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageResponseDto imageResponseDto = new ImageResponseDto();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final String fileName = user.getId() + user.getUsername() + timestamp.getTime() + ".png";
        imageResponseDto.setName(fileName);
        imageResponseDto.setImage(scannerImagesService.mergeImage(upper, lower, user, fileName));
        return imageResponseDto;
    }
}
