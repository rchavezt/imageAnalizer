package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.Patient;
import com.triple.o.labs.imageAnalizer.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PatientDao extends CrudRepository<Patient, Long> {
    Set<Patient> findAllByDoctorUser(User user);
}
