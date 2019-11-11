package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.image.ImageStatusResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.Snapshot;
import com.triple.o.labs.imageAnalizer.entities.Stl;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.SnapshotService;
import com.triple.o.labs.imageAnalizer.services.StlService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/image", headers = "Authorization")
public class ScannerImagesController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private StlService stlService;

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_SCAN_IMAGES_VIEW")
    @RequestMapping(value = "/stl/case/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getStl(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB) {
            throw new BadRequestException("User must be Laboratory");
        }

        MedicalCase medicalCase = caseService.getCase(id);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(medicalCase.getStl().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + medicalCase.getStl().getFileName() + "\"")
                .body(new ByteArrayResource(medicalCase.getStl().getBase64file()));
    }

    @Secured("ROLE_SCAN_IMAGES_UPLOAD")
    @RequestMapping(value = "/stl/add", method = RequestMethod.POST, produces = "application/json")
    public ImageStatusResponseDto addStl(@CurrentUser UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file){

        Stl stl = stlService.storeFile(file);

        ImageStatusResponseDto imageStatusResponseDto = new ImageStatusResponseDto();
        imageStatusResponseDto.setId(stl.getId());
        imageStatusResponseDto.setSuccess(true);

        return imageStatusResponseDto;
    }

    @Secured("ROLE_SCAN_IMAGES_UPLOAD")
    @RequestMapping(value = "/snapshot/add/case/{id}", method = RequestMethod.POST, produces = "application/json")
    public ImageStatusResponseDto addSnapshot(@CurrentUser UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB) {
            throw new BadRequestException("User must be Laboratory");
        }
        Snapshot snapshot = snapshotService.storeFile(file);

        ImageStatusResponseDto imageStatusResponseDto = new ImageStatusResponseDto();
        imageStatusResponseDto.setId(snapshot.getId());

        try {
            MedicalCase medicalCase = caseService.getCase(id);
            caseService.addSnapshot(medicalCase, snapshot, user.getUsername());
        } catch (Exception e) {
            throw new BadRequestException("Error uploading snapshot", e);
        }

        imageStatusResponseDto.setSuccess(true);
        return imageStatusResponseDto;
    }
}
