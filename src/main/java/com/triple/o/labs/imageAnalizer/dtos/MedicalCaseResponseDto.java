package com.triple.o.labs.imageAnalizer.dtos;

import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MedicalCaseResponseDto {
    private Long id;
    private String detail;
    private String doctorFullName;
    private PatientDto patient;
    private Status status;
    private Set<String> medicalCaseImagesUrl;
}
