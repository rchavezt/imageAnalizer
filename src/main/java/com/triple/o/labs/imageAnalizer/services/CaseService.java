package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.List;
import java.util.Set;

public interface CaseService {
    List<MedicalCase> getCases();
    MedicalCase getCase(Long id);
    Set<MedicalCase> getCasesByDoctor(User user);
    MedicalCase editMedicatCase(Long id, MedicalCaseDto medicalCaseDto, String userEditing);
    MedicalCase createMedicalCase(User user, MedicalCaseDto medicalCase, String userCreating);
}
