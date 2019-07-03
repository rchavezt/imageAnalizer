package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public Set<MedicalCaseDto> getMedicalCasebyDoctor(@PathVariable Long id){
        Optional<User> user = usersDao.findById(id);
        return caseService.getCasesByDoctor(user.get());
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json")
    public MedicalCaseDto createMedicatCase(@RequestBody MedicalCaseDto medicalCaseDto) throws Exception {

        caseService.
    }

}
