package com.triple.o.labs.imageAnalizer.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialMedicalCaseDto extends MedicalCaseRequestDto {
    Long stlId;
}
