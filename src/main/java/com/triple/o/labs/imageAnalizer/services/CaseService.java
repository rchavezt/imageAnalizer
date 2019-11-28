package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.MedicalCaseRequestDto;
import com.triple.o.labs.imageAnalizer.entities.*;

import java.util.List;
import java.util.Set;

public interface CaseService {
    List<MedicalCase> getCases();
    MedicalCase getCase(Long id);
    List<MedicalCase> getCasesByDoctor(User user);
    MedicalCase addModels(MedicalCase medicalCase, MedicalCaseImage medicalCaseImage, String userEditing);
    MedicalCase addSnapshot(MedicalCase medicalCase, Snapshot snapshotImageAnalyzed, String userEditing);
    MedicalCase editMedicalCase(Long id, MedicalCaseDto medicalCaseDto, String userEditing);
    MedicalCase createMedicalCase(User user, MedicalCaseRequestDto medicalCase, Stl stl, String userCreating);
    MedicalCase removePairPoints(MedicalCase medicalCase);
}
