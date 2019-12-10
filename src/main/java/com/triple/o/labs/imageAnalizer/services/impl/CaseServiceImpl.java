package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.AppliancesDto;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.MedicalCaseRequestDto;
import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.Status;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return casesDao.findAllByOrderByDateUpdatedDesc();
    }

    @Override
    public MedicalCase getCase(Long id) {
        return casesDao.findById(id).get();
    }

    @Override
    public List<MedicalCase> getCasesByDoctor(User user) {
        return casesDao.findByUserOrderByDateUpdatedDesc(user);
    }

    @Override
    public MedicalCase addModels(MedicalCase medicalCase, Image medicalCaseImage, String userEditing) {
        medicalCase.setMedicalCaseImage(medicalCaseImage);
        medicalCase.setStatus(Status.MODELED);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }

    @Override
    public MedicalCase addSnapshot(MedicalCase medicalCase, Image snapshotImageAnalyzed, String userEditing) {
        medicalCase.setSnapshotImageAnalyzed(snapshotImageAnalyzed);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }

    @Override
    public MedicalCase addBilmer(MedicalCase medicalCase, Image bilmer, String userEditing) {
        medicalCase.setBilmer(bilmer);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }

    @Override
    public MedicalCase addExtra(MedicalCase medicalCase, Image extra, String userEditing) {
        List<Image> extras = medicalCase.getExtraImages();

        if (extras == null || extras.isEmpty()) {
            extras = new ArrayList<>();
        }

        extras.add(extra);
        medicalCase.setExtraImages(extras);

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
    public MedicalCase createMedicalCase(User user, MedicalCaseRequestDto medicalCaseRequestDto, Image stl, String userCreating) {
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

    @Override
    public String addObservations(Long id, String observations, String userEditing) {
        MedicalCase medicalCase = casesDao.findById(id).get();
        medicalCase.setObservations(observations);
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase).getObservations();
    }

    @Override
    public MedicalCase addAppliances(Long id, AppliancesDto appliances, String userEditing) {
        MedicalCase medicalCase = casesDao.findById(id).get();
        medicalCase.setUpperAppliance(appliances.getUpperAppliance());
        medicalCase.setLowerAppliance(appliances.getLowerAppliance());
        medicalCase.setUpdatedBy(userEditing);
        return casesDao.save(medicalCase);
    }
}
