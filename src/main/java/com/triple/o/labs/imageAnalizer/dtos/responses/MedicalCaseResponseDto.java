package com.triple.o.labs.imageAnalizer.dtos.responses;

import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.points.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.enums.AnalysisType;
import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalCaseResponseDto {
    private Long id;
    private String detail;
    private String doctorFullName;
    private PatientDto patient;
    private Status status;
    private AnalysisType analysisType;
    private byte[] medicalCaseImage;
    private byte[] medicalCaseImageAnalyzed;
    private List<SchwarzKorkhausDto> pairPoints;
}
