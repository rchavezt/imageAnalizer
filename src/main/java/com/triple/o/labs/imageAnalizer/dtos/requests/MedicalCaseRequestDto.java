package com.triple.o.labs.imageAnalizer.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalCaseRequestDto {
    private Long id;
    private String detail;
    private Long patientId;
}
