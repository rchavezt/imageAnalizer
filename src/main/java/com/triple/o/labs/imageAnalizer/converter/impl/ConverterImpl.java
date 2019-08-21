package com.triple.o.labs.imageAnalizer.converter.impl;

import com.triple.o.labs.imageAnalizer.converter.Converter;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.dtos.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.SchwarzKorkhausPairPoint;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConverterImpl implements Converter {
    @Override
    public MedicalCaseResponseDto convertMedicalCase(MedicalCase medicalCase) {
        MedicalCaseResponseDto medicalCaseResponseDto = new MedicalCaseResponseDto();
        BeanUtils.copyProperties(medicalCase, medicalCaseResponseDto);
        medicalCaseResponseDto.setPairPoints(new ArrayList<>());
        medicalCaseResponseDto.setDoctorFullName(medicalCase.getUser().getName());
        medicalCaseResponseDto.setMedicalCaseImage(medicalCase.getMedicalCaseImage().getBase64image());

        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(medicalCase.getPatient(), patientDto);
        medicalCaseResponseDto.setPatient(patientDto);

        for (SchwarzKorkhausPairPoint schwarzKorkhausPairPoint : medicalCase.getPairPoints()){
            SchwarzKorkhausDto schwarzKorkhausDto = new SchwarzKorkhausDto();
            BeanUtils.copyProperties(schwarzKorkhausPairPoint, schwarzKorkhausDto);
            medicalCaseResponseDto.getPairPoints().add(schwarzKorkhausDto);
        }

        return medicalCaseResponseDto;
    }
}
