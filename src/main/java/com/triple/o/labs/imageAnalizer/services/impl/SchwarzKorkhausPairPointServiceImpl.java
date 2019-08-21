package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.daos.SchwarzKorkhausPairPointDao;
import com.triple.o.labs.imageAnalizer.dtos.SchwarzKorkhausDto;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.SchwarzKorkhausPairPoint;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.SchwarzKorkhausPairPointService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchwarzKorkhausPairPointServiceImpl implements SchwarzKorkhausPairPointService {

    @Autowired
    private SchwarzKorkhausPairPointDao schwarzKorkhausPairPointDao;

    @Autowired
    private CasesDao casesDao;

    @Override
    public List<SchwarzKorkhausPairPoint> savePairPoints(MedicalCase medicalCase, List<SchwarzKorkhausDto> schwarzKorkhausDtoList) {
        List<SchwarzKorkhausPairPoint> pointList = new ArrayList<>();

        for(SchwarzKorkhausDto schwarzKorkhausDto : schwarzKorkhausDtoList){
            SchwarzKorkhausPairPoint schwarzKorkhausPairPoint = new SchwarzKorkhausPairPoint();
            BeanUtils.copyProperties(schwarzKorkhausDto, schwarzKorkhausPairPoint);
            schwarzKorkhausPairPoint.setMedicalCase(medicalCase);
            pointList.add(schwarzKorkhausPairPointDao.save(schwarzKorkhausPairPoint));
        }

        medicalCase.setPairPoints(pointList);
        casesDao.save(medicalCase);

        return pointList;
    }
}
