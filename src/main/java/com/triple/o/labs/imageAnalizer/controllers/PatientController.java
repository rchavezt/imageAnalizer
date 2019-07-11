package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.GET, produces = "application/json")
    public Set<PatientDto> getPatientByDoctor(@PathVariable Long id) {
        User doctor = userService.getUser(id);
        Set<Patient> patients = patientService.getPatientsByDoctor(doctor);

        Set<PatientDto> patientDtoSet = new HashSet<>();

        for (Patient patient : patients) {
            PatientDto patientDto = new PatientDto();
            BeanUtils.copyProperties(patient, patientDto);
            patientDtoSet.add(patientDto);
        }

        return patientDtoSet;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public PatientDto addPatient(@RequestBody PatientDto patientDto) {
        Patient patient = patientService.addPatient(patientDto);
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }
}
