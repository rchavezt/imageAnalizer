package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.SchwarzKorkhausPairPoint;

import java.util.List;

public interface SchwarzKorkhausPairPointService {
    List<SchwarzKorkhausPairPoint> savePairPoints(MedicalCase medicalCase, List<SchwarzKorkhausDto> schwarzKorkhausDtoList);
}