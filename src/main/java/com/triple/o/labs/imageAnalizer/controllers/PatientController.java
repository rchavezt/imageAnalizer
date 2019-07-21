package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.Role;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.RoleName;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/patient", headers = "Authorization")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Secured("ROLE_PATIENT_VIEW")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public PatientDto getPatient(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) throws Exception {
        User user = userService.getUser(userPrincipal.getId());
        Patient patient = patientService.getPatient(id);

        if (user.getUserType() == UserType.DOCTOR && patient.getDoctorUser() != user)
            throw new BadRequestException("Patient you are trying to view is assigned to another Doctor");

        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(patient, patientDto);

        return patientDto;
    }

    @Secured("ROLE_PATIENT_LIST")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public Set<PatientDto> getPatients(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.getUser(userPrincipal.getId());
        Boolean isDoctor = null;
        if (user.getUserType() == UserType.DOCTOR) {
            isDoctor = true;
        } else if (user.getUserType() == UserType.LAB) {
            isDoctor = false;
        }

        if (isDoctor == null){
            throw new BadRequestException("User must be Doctor or Laboratory");
        }

        List<Patient> patients;
        if (isDoctor) {
            patients = new ArrayList<>(patientService.getPatientsByDoctor(user));
        } else {
            patients = patientService.getAll();
        }

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
        if (user.getUserType() != UserType.DOCTOR)
                throw new BadRequestException("User must be Doctor");
        Patient patient = patientService.addPatient(user, patientDto);
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }

    @PreAuthorize("hasRole('ROLE_PATIENT_CREATE') and hasRole('ROLE_PATIENT_ASSIGN')")
    @RequestMapping(value = "/add/doctor/{id}", method = RequestMethod.POST, produces = "application/json")
    public PatientDto addPatientToDoctor(@CurrentUser UserPrincipal userPrincipal, @RequestBody PatientDto patientDto, @PathVariable Long idDoctor) {
        User user = userService.getUser(userPrincipal.getId());
        User userDoctor = userService.getUser(idDoctor);
        if (user.getUserType() != UserType.LAB)
            throw new BadRequestException("User must be Laboratory");
        Patient patient = patientService.addPatient(userDoctor, patientDto);
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }

    @Secured("ROLE_PATIENT_EDIT")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT, produces = "application/json")
    public PatientDto editPatient(@CurrentUser UserPrincipal userPrincipal, @RequestBody PatientDto patientDto, @PathVariable Long id) {
        User user = userService.getUser(userPrincipal.getId());
        Patient patient = patientService.getPatient(id);

        if (user.getUserType() == UserType.DOCTOR && patient.getDoctorUser() != user)
            throw new BadRequestException("Patient you are trying to edit is assigned to another Doctor");

        patient = patientService.editPatient(id, patientDto);
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }

    @Secured("ROLE_PATIENT_DELETE")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public PatientDto deactivatePatient(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        User user = userService.getUser(userPrincipal.getId());
        Patient patient = patientService.getPatient(id);

        if (user.getUserType() == UserType.DOCTOR && patient.getDoctorUser() != user)
            throw new BadRequestException("Patient you are trying to edit is assigned to another Doctor");

        patient = patientService.deactivatePatient(id);
        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }
}
