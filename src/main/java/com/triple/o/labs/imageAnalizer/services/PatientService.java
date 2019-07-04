package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.Set;

public interface PatientService {
    Patient getPatient(Long id);
    Set<Patient> getPatientsByDoctor(User doctor);
    Patient addPatient(PatientDto patientDto);
}
