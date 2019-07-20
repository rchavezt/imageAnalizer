package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.RoleName;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/patient", headers = "Authorization")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_PATIENT_LIST")
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public Set<PatientDto> getPatientByDoctor(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUser(userPrincipal.getId());
        Set<Patient> patients = patientService.getPatientsByDoctor(user);
        Set<PatientDto> patientDtoSet = new HashSet<>();

        for (Patient patient : patients) {
            PatientDto patientDto = new PatientDto();
            BeanUtils.copyProperties(patient, patientDto);
            patientDto.setFullName(patient.getFirstName().concat(" ").concat(patient.getLastName()));
            patientDtoSet.add(patientDto);
        }

        return patientDtoSet;
    }

    @Secured("ROLE_PATIENT_CREATE")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public PatientDto addPatient(@CurrentUser UserPrincipal userPrincipal, @RequestBody PatientDto patientDto) {
        User user = userService.getUser(userPrincipal.getId());
        Patient patient = patientService.addPatient(user, patientDto);
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }
}
