package com.triple.o.labs.imageAnalizer.dtos.requests;

import com.triple.o.labs.imageAnalizer.dtos.image.ImageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalCaseRequestDto {
    private Long id;
    private String detail;
    private Long patientId;
    private ImageRequestDto medicalCaseImages;
}
