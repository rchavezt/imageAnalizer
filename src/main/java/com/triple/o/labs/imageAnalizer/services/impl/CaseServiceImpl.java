package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.MedicalCaseRequestDto;
import com.triple.o.labs.imageAnalizer.entities.*;
import com.triple.o.labs.imageAnalizer.enums.Status;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<MedicalCase> getCases() {
        return (List<MedicalCase>) casesDao.findAll();
    }

    @Override
    public MedicalCase getCase(Long id) {
        return casesDao.findById(id).get();
    }

    @Override
    public Set<MedicalCase> getCasesByDoctor(User user) {
        return casesDao.findByUser(user);
    }

    @Override
    public MedicalCase addModels(MedicalCase medicalCase, MedicalCaseImage medicalCaseImage, String userEditing) {
        medicalCase.setMedicalCaseImage(medicalCaseImage);
        medicalCase.setStatus(Status.MODELED);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }

    @Override
    public MedicalCase editMedicalCase(Long id, MedicalCaseDto medicalCaseDto, String userEditing) {
        MedicalCase medicalCase = getCase(id);
        BeanUtils.copyProperties(medicalCaseDto, medicalCase);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }

    @Override
    public MedicalCase createMedicalCase(User user, MedicalCaseRequestDto medicalCaseRequestDto, Stl stl, String userCreating) {
        MedicalCase medicalCase = new MedicalCase();
        BeanUtils.copyProperties(medicalCaseRequestDto, medicalCase);
        Patient patient = patientService.getPatient(medicalCaseRequestDto.getPatientId());
        medicalCase.setPatient(patient);
        medicalCase.setUser(user);
        medicalCase.setStatus(Status.NEW);
        medicalCase.setStl(stl);
        medicalCase.setCreatedBy(userCreating);
        medicalCase.setUpdatedBy(userCreating);
        return casesDao.save(medicalCase);
    }
}
