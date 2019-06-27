package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_LABORATORY')")
    public Set<MedicalCaseDto> getMedicalCasebyDoctor(@PathVariable Long id){
        Optional<User> user = usersDao.findById(id);
        return caseService.getCasesByDoctor(user.get());
    }

}
