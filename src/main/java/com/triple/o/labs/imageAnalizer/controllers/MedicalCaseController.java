package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.converter.Converter;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.MedicalCaseRequestDto;
import com.triple.o.labs.imageAnalizer.dtos.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import com.triple.o.labs.imageAnalizer.entities.SchwarzKorkhausPairPoint;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/case", headers = "Authorization")
public class MedicalCaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private ScannerImagesService scannerImagesService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SchwarzKorkhausPairPointService schwarzKorkhausPairPointService;

    @Autowired
    private Converter converter;

    @Secured("ROLE_CASES_LIST")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public Set<MedicalCaseResponseDto> getMedicalCases(@CurrentUser UserPrincipal userPrincipal){
        User user = userService.getUser(userPrincipal.getId());

        if (user.getUserType() != UserType.DOCTOR && user.getUserType() != UserType.LAB)
            throw new BadRequestException("User should be Doctor or Laboratory");

        List<MedicalCase> medicalCases;

        if (user.getUserType() == UserType.DOCTOR) {
            medicalCases = new ArrayList<>(caseService.getCasesByDoctor(user));
        } else {
            medicalCases = caseService.getCases();
        }
        Set<MedicalCaseResponseDto> response = new HashSet<>();
        for (MedicalCase medicalCase : medicalCases){
            MedicalCaseResponseDto medicalCaseResponse = new MedicalCaseResponseDto();
            BeanUtils.copyProperties(medicalCase, medicalCaseResponse);
            PatientDto patientDto = new PatientDto();
            BeanUtils.copyProperties(medicalCase.getPatient(), patientDto);
            medicalCaseResponse.setPatient(patientDto);
            medicalCaseResponse.setDoctorFullName(user.getName());
            response.add(medicalCaseResponse);
        }
        return response;
    }

    @Secured("ROLE_CASES_VIEW")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public MedicalCaseResponseDto getMedicatCase(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB && user.getUserType() != UserType.DOCTOR)
            throw new BadRequestException("User must be Doctor or Laboratory");

        MedicalCase medicalCase = caseService.getCase(id);

        if (UserType.DOCTOR == user.getUserType() && medicalCase.getUser() != user){
            throw new BadRequestException("Case you are trying to get is assigned to another doctor");
        }

        return converter.convertMedicalCase(medicalCase);
    }

    @Secured("ROLE_CASES_EDIT")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT, produces = "application/json")
    public MedicalCaseDto editMedicatCase(@CurrentUser UserPrincipal userPrincipal, @RequestBody MedicalCaseDto medicalCaseDto, @PathVariable Long id){
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB && user.getUserType() != UserType.DOCTOR)
            throw new BadRequestException("User must be Doctor or Laboratory");

        MedicalCase medicalCase = caseService.getCase(id);

        if (UserType.DOCTOR == user.getUserType() && medicalCase.getUser() != user){
            throw new BadRequestException("Case you are trying to edit is assigned to another doctor");
        }

        BeanUtils.copyProperties(caseService.editMedicatCase(id, medicalCaseDto, user.getUsername()), medicalCaseDto);
        return medicalCaseDto;
    }

    @Secured("ROLE_CASES_CREATE")
    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public MedicalCaseResponseDto createMedicalCase(@CurrentUser UserPrincipal userPrincipal, @RequestBody MedicalCaseRequestDto medicalCaseRequestDto) {
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.DOCTOR)
            throw new BadRequestException("User must be Doctor");

        MedicalCaseImage imageCase = scannerImagesService.saveMedicalCaseImage(medicalCaseRequestDto.getPatientId(), medicalCaseRequestDto.getMedicalCaseImages().getUpper(), medicalCaseRequestDto.getMedicalCaseImages().getLower());

        MedicalCase medicalCase = caseService.createMedicalCase(user, medicalCaseRequestDto, imageCase, user.getUsername());

        String notificationMessage = String.format("New case ID: %d for Doctor: %s", medicalCase.getId(), medicalCase.getUser().getName());
        notificationService.createNotification(notificationMessage);

        return converter.convertMedicalCase(medicalCase);
    }

    @PreAuthorize("hasRole('ROLE_CASES_CREATE') and hasRole('ROLE_DOCTOR_ASSIGN')")
    @RequestMapping(value = "/new/doctor/{id}", method = RequestMethod.POST, produces = "application/json")
    public MedicalCaseResponseDto createMedicalCaseToDoctor(@CurrentUser UserPrincipal userPrincipal, @RequestBody MedicalCaseRequestDto medicalCaseRequestDto, @PathVariable Long id) {
        User user = userService.getUser(userPrincipal.getId());
        User userDoctor = userService.getUser(id);
        if (user.getUserType() != UserType.LAB)
            throw new BadRequestException("User must be Laboratory");

        MedicalCaseImage imageCase = scannerImagesService.saveMedicalCaseImage(medicalCaseRequestDto.getPatientId(), medicalCaseRequestDto.getMedicalCaseImages().getUpper(), medicalCaseRequestDto.getMedicalCaseImages().getLower());

        MedicalCase medicalCase = caseService.createMedicalCase(userDoctor, medicalCaseRequestDto, imageCase, user.getUsername());
        return converter.convertMedicalCase(medicalCase);
    }

    @Secured("ROLE_CASES_EDIT")
    @RequestMapping(value = "/add/schwarzKorkhausPairPoints/{id}", method = RequestMethod.PUT, produces = "application/json")
    public List<SchwarzKorkhausDto> setSchwarzKorkhausPairPoints(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id, @RequestBody List<SchwarzKorkhausDto> request) {
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB)
            throw new BadRequestException("User must be Laboratory");

        MedicalCase medicalCase = caseService.getCase(id);

        List<SchwarzKorkhausPairPoint> pairPoints = schwarzKorkhausPairPointService.savePairPoints(medicalCase, request);

        List<SchwarzKorkhausDto> response = new ArrayList<>();
        for (SchwarzKorkhausPairPoint schwarzKorkhausPairPoint : pairPoints){
            SchwarzKorkhausDto schwarzKorkhausDto = new SchwarzKorkhausDto();
            BeanUtils.copyProperties(schwarzKorkhausPairPoint, schwarzKorkhausDto);
            response.add(schwarzKorkhausDto);
        }
        return response;
    }

}
