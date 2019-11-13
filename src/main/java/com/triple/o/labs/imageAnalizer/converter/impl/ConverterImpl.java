package com.triple.o.labs.imageAnalizer.converter.impl;

import com.triple.o.labs.imageAnalizer.converter.Converter;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.points.PositionDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.points.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseResponseDto;
import com.triple.o.labs.imageAnalizer.dtos.responses.MedicalCaseSimpleResponseDto;
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

        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(medicalCase.getPatient(), patientDto);
        medicalCaseResponseDto.setPatient(patientDto);
        if (medicalCase.getMedicalCaseImage() != null) {
            medicalCaseResponseDto.setMedicalCaseImage(medicalCase.getMedicalCaseImage().getBase64image());
        }

        if(medicalCase.getPairPoints() != null) {
            for (SchwarzKorkhausPairPoint schwarzKorkhausPairPoint : medicalCase.getPairPoints()) {
                SchwarzKorkhausDto schwarzKorkhausDto = new SchwarzKorkhausDto();
                PositionDto positionDto = new PositionDto();
                BeanUtils.copyProperties(schwarzKorkhausPairPoint, schwarzKorkhausDto);
                positionDto.setX(schwarzKorkhausPairPoint.getPointX());
                positionDto.setY(schwarzKorkhausPairPoint.getPointY());
                schwarzKorkhausDto.setPosition(positionDto);
                medicalCaseResponseDto.getPairPoints().add(schwarzKorkhausDto);
            }
        }

        return medicalCaseResponseDto;
    }

    @Override
    public MedicalCaseSimpleResponseDto convertSimpleMedicalCase(MedicalCase medicalCase) {
        MedicalCaseSimpleResponseDto medicalCaseSimpleResponseDto = new MedicalCaseSimpleResponseDto();
        BeanUtils.copyProperties(medicalCase, medicalCaseSimpleResponseDto);
        medicalCaseSimpleResponseDto.setDoctorFullName(medicalCase.getUser().getName());

        PatientDto patientDto = new PatientDto();
        BeanUtils.copyProperties(medicalCase.getPatient(), patientDto);
        medicalCaseSimpleResponseDto.setPatient(patientDto);

        return medicalCaseSimpleResponseDto;
    }
}
