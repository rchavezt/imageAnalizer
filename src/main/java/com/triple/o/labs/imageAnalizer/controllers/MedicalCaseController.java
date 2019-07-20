package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/case", headers = "Authorization")
public class MedicalCaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    UsersDao usersDao;

    @Secured("ROLE_CASES_LIST")
    @RequestMapping(value = "/get/user", method = RequestMethod.GET, produces = "application/json")
    public Set<MedicalCaseResponseDto> getMedicalCasebyDoctor(@CurrentUser UserPrincipal userPrincipal){
        User doctor = usersDao.findById(userPrincipal.getId()).get();
        Set<MedicalCase> medicalCases = caseService.getCasesByDoctor(doctor);
        Set<MedicalCaseResponseDto> response = new HashSet<>();
        for (MedicalCase medicalCase : medicalCases){
            MedicalCaseResponseDto medicalCaseResponse = new MedicalCaseResponseDto();
            BeanUtils.copyProperties(medicalCase, medicalCaseResponse);
            PatientDto patientDto = new PatientDto();
            BeanUtils.copyProperties(medicalCase.getPatient(), patientDto);
            medicalCaseResponse.setPatient(patientDto);
            medicalCaseResponse.setDoctorFullName(doctor.getName());
            response.add(medicalCaseResponse);
        }
        return response;
    }

    @Secured("ROLE_CASES_CREATE")
    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public MedicalCaseDto createMedicatCase(@CurrentUser UserPrincipal userPrincipal, @RequestBody MedicalCaseDto medicalCaseDto) throws Exception {
        User user = usersDao.findById(userPrincipal.getId()).get();
        MedicalCase medicalCase = caseService.createMedicalCase(user, medicalCaseDto);
        BeanUtils.copyProperties(medicalCase, medicalCaseDto);
        return medicalCaseDto;
    }

}
