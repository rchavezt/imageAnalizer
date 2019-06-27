package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.MedicalCaseDto;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.Set;

public interface CaseService {
    Set<MedicalCaseDto> getCasesByDoctor(User user);
}
