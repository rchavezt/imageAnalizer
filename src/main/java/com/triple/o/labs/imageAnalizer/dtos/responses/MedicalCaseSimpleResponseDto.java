package com.triple.o.labs.imageAnalizer.dtos.responses;

import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.enums.AnalysisType;
import com.triple.o.labs.imageAnalizer.enums.Anomaly;
import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalCaseSimpleResponseDto {
    private Long id;
    private String detail;
    private String doctorFullName;
    private PatientDto patient;
    private Status status;
    private AnalysisType analysisType;
    private Anomaly anomaly;
}
