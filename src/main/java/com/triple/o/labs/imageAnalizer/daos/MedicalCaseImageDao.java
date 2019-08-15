package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import org.springframework.data.repository.CrudRepository;

public interface MedicalCaseImageDao extends CrudRepository<MedicalCaseImage, Long> {
}
