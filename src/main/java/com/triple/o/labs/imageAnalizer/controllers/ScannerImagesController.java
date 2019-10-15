package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.image.StlResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.ScannerImagesService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;
import java.util.Set;

@RestController
@RequestMapping(value = "/image", headers = "Authorization")
public class ScannerImagesController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private ScannerImagesService scannerImagesService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_SCAN_IMAGES_VIEW")
    @RequestMapping(value = "/stl/case/{id}", method = RequestMethod.GET, produces = "application/json")
    public StlResponseDto getStl(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB)
            throw new BadRequestException("User must be Laboratory");

        MedicalCase medicalCase = caseService.getCase(id);

        StlResponseDto stlResponseDto = new StlResponseDto();
        stlResponseDto.setImage(medicalCase.getStl().getBase64image());

        return stlResponseDto;
    }
}
