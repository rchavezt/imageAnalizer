package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.PatientDao;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.PatientDto;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private UsersDao usersDao;

    @Override
    public Patient getPatient(Long id) {
        return patientDao.findById(id).get();
    }

    @Override
    public Set<Patient> getPatientsByDoctor(User doctor) {
        return patientDao.findAllByDoctorUser(doctor);
    }

    @Override
    public Patient addPatient(User user, PatientDto patientDto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDto, patient);
        patient.setDoctorUser(user);
        return patientDao.save(patient);
    }
}
