package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CasesDao casesDao;

    @Override
    public Set<MedicalCaseDto> getCasesByDoctor(User user) {
        Set<MedicalCase> medicalCases = casesDao.findByUser(user);
        Set<MedicalCaseDto> medicalCaseDtos = new HashSet<>();

        for (MedicalCase medicalCase : medicalCases){
            MedicalCaseDto medicalCaseDto = new MedicalCaseDto();
            BeanUtils.copyProperties(medicalCase, medicalCaseDto);
            medicalCaseDtos.add(medicalCaseDto);
        }

        return medicalCaseDtos;
    }
}
