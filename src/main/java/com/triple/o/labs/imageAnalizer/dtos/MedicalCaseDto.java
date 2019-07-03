package com.triple.o.labs.imageAnalizer.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MedicalCaseDto {
    private Long id;
    private String detail;
    private Long patientId;
    private Set<String> medicalCaseImagesUrl;
}
