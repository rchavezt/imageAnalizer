package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CasesDao extends CrudRepository<MedicalCase, Long> {
    Set<MedicalCase> findByUser(User user);
}
