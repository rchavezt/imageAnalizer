package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CasesDao casesDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PatientService patientService;

    @Override
    public Set<MedicalCase> getCasesByDoctor(User user) {
        return casesDao.findByUser(user);
    }

    @Override
    public MedicalCase createMedicalCase(User user, MedicalCaseDto medicalCaseDto) throws Exception {
        MedicalCase medicalCase = new MedicalCase();
        BeanUtils.copyProperties(medicalCaseDto, medicalCase);

        try {
            Patient patient = patientService.getPatient(medicalCaseDto.getPatientId());
            medicalCase.setPatient(patient);
            medicalCase.setUser(user);
            return casesDao.save(medicalCase);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
