package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.List;
import java.util.Set;

public interface PatientService {
    Patient getPatient(Long id);
    List<Patient> getAll();
    Set<Patient> getPatientsByDoctor(User doctor);
    Patient addPatient(User user,PatientDto patientDto);
    Patient editPatient(Long id, PatientDto patientDto);
    Patient deactivatePatient(Long id);
}
