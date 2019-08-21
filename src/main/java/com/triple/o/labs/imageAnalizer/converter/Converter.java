package com.triple.o.labs.imageAnalizer.converter;

import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;

import java.util.List;

public interface Converter {
    MedicalCaseResponseDto convertMedicalCase(MedicalCase medicalCase);
}
