package com.triple.o.labs.imageAnalizer.converter;

import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseSimpleResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;

public interface Converter {
    MedicalCaseResponseDto convertMedicalCase(MedicalCase medicalCase);
    MedicalCaseSimpleResponseDto convertSimpleMedicalCase(MedicalCase medicalCase);
}
