package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.image.ImageResponseDto;
import com.triple.o.labs.imageAnalizer.dtos.image.ImageStatusResponseDto;
import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.ImageType;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.exceptions.ResourceNotFoundException;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.ImageService;
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

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/image", headers = "Authorization")
public class ImageController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private ImageService imageService;

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

    @Secured("ROLE_SCAN_IMAGES_VIEW")
    @RequestMapping(value = "/extras/case/{id}", method = RequestMethod.GET)
    public List<ImageResponseDto> getExtras(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());

        if (user.getUserType() != UserType.DOCTOR && user.getUserType() != UserType.LAB){
            throw new BadRequestException("User should be Doctor or Laboratory");
        }

        MedicalCase medicalCase = caseService.getCase(id);

        if (user.getUserType() == UserType.DOCTOR && medicalCase.getUser() != user) {
            throw new BadRequestException("Te image's case you are trying yo view belongs to another Doctor.");
        }

        List<ImageResponseDto> response = new ArrayList<>();

        medicalCase.getExtraImages().forEach(e -> {
            ImageResponseDto responseDto = new ImageResponseDto();
            responseDto.setName(e.getFileName());
            responseDto.setImage(e.getBase64file());
            response.add(responseDto);
        });

        return response;
    }

    @Secured("ROLE_SCAN_IMAGES_VIEW")
    @RequestMapping(value = "/base/{imageType}/case/{id}", method = RequestMethod.GET)
    public ImageResponseDto getImage(@CurrentUser UserPrincipal userPrincipal, @PathVariable ImageType imageType, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());

        if (user.getUserType() != UserType.DOCTOR && user.getUserType() != UserType.LAB){
            throw new BadRequestException("User should be Doctor or Laboratory");
        }

        MedicalCase medicalCase = caseService.getCase(id);

        if (user.getUserType() == UserType.DOCTOR && medicalCase.getUser() != user) {
            throw new BadRequestException("Te image's case you are trying yo view belongs to another Doctor.");
        }

        Image image;

        switch (imageType) {
            case original:
                if (medicalCase.getMedicalCaseImage() != null) {
                    image = medicalCase.getMedicalCaseImage();
                } else {
                    throw new ResourceNotFoundException("Original image", "Medical Case", id);
                }
                break;
            case stl:
                if (medicalCase.getStl() != null) {
                    image = medicalCase.getStl();
                } else {
                    throw new ResourceNotFoundException("STL", "Medical Case", id);
                }
                break;
            case analyzed:
                if (medicalCase.getSnapshotImageAnalyzed() != null) {
                    image = medicalCase.getSnapshotImageAnalyzed();
                } else {
                    throw new ResourceNotFoundException("Analyzed image", "Medical Case", id);
                }
                break;
            case extra:
                throw new BadRequestException("Extras could not be fetch by this endpoint.");
            case bilmer:
                if (medicalCase.getBilmer() != null) {
                    image = medicalCase.getBilmer();
                } else {
                    throw new ResourceNotFoundException("Bilmer image", "Medical Case", id);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + imageType + "is not valid or is used by another EP.");
        }

        ImageResponseDto imageResponseDto = new ImageResponseDto();

        imageResponseDto.setImage(image.getBase64file());
        imageResponseDto.setName(imageType.toString());

        return imageResponseDto;
    }


    @Secured("ROLE_SCAN_IMAGES_UPLOAD")
    @RequestMapping(value = "/stl/add", method = RequestMethod.POST, produces = "application/json")
    public ImageStatusResponseDto addStl(@CurrentUser UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file){

        Image stl = imageService.storeFile(file, ImageType.stl);

        ImageStatusResponseDto imageStatusResponseDto = new ImageStatusResponseDto();
        imageStatusResponseDto.setId(stl.getId());
        imageStatusResponseDto.setSuccess(true);

        return imageStatusResponseDto;
    }

    @Secured("ROLE_SCAN_IMAGES_UPLOAD")
    @RequestMapping(value = "/{imageType}/add/case/{id}", method = RequestMethod.POST, produces = "application/json")
    public ImageStatusResponseDto addImage(@CurrentUser UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file, @PathVariable ImageType imageType, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());

        if (imageType == ImageType.stl){
            throw new BadRequestException("Use /stl/add endpoint to add STL and then assign it to the case on NEW case");
        }

        if (user.getUserType() != UserType.LAB && user.getUserType() != UserType.DOCTOR) {
            throw new BadRequestException("User must be Doctor or Laboratory");
        }

        ImageStatusResponseDto imageStatusResponseDto = new ImageStatusResponseDto();
        MedicalCase medicalCase = caseService.getCase(id);

        Image image = imageService.storeFile(file, imageType);
        imageStatusResponseDto.setId(image.getId());

        switch (imageType){
            case analyzed:
                try {
                    caseService.addSnapshot(medicalCase, image, user.getUsername());
                } catch (Exception e) {
                    throw new BadRequestException("Error uploading snapshot", e);
                }
                break;
            case extra:
                try {
                    caseService.addExtra(medicalCase, image, user.getUsername());
                } catch (Exception e) {
                    throw new BadRequestException("Error uploading extra", e);
                }
                break;
            case bilmer:
                try {
                    caseService.addBilmer(medicalCase, image, user.getUsername());
                } catch (Exception e) {
                    throw new BadRequestException("Error uploading bilmer", e);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + imageType + "is not valid or is used on another EP.");
        }

        imageStatusResponseDto.setSuccess(true);
        return imageStatusResponseDto;
    }
}
