package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/case")
public class MedicalCaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    UsersDao usersDao;

    @RequestMapping(value = "/get/doctor/{id}", method = RequestMethod.GET, produces = "application/json")
    //@PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_LABORATORY')")
    public Set<MedicalCaseResponseDto> getMedicalCasebyDoctor(@PathVariable Long id){
        User doctor = usersDao.findById(id).get();
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

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public MedicalCaseDto createMedicatCase(@RequestBody MedicalCaseDto medicalCaseDto) throws Exception {
        MedicalCase medicalCase = caseService.createMedicalCase(medicalCaseDto);
        BeanUtils.copyProperties(medicalCase, medicalCaseDto);
        return medicalCaseDto;
    }

}
