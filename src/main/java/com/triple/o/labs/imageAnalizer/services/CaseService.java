package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.AppliancesDto;
import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.MedicalCaseRequestDto;
import com.triple.o.labs.imageAnalizer.entities.Image;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.List;

public interface CaseService {
    List<MedicalCase> getCases();
    MedicalCase getCase(Long id);
    List<MedicalCase> getCasesByDoctor(User user);
    MedicalCase addModels(MedicalCase medicalCase, Image medicalCaseImage, String userEditing);
    MedicalCase addSnapshot(MedicalCase medicalCase, Image snapshotImageAnalyzed, String userEditing);
    MedicalCase addBilmer(MedicalCase medicalCase, Image bilmer, String userEditing);
    MedicalCase addCanvas(MedicalCase medicalCase, Image canvas, String userEditing);
    MedicalCase addAnalyzedBlue(MedicalCase medicalCase, Image analyzedBlue, String userEditing);
    MedicalCase addExtra(MedicalCase medicalCase, Image extra, String userEditing);
    MedicalCase editMedicalCase(Long id, MedicalCaseDto medicalCaseDto, String userEditing);
    MedicalCase createMedicalCase(User user, MedicalCaseRequestDto medicalCase, Image stl, String userCreating);
    String addObservations(Long id, String observations, String userEditing);
    MedicalCase addAppliances(Long id, AppliancesDto appliances, String userEditing);
}
