package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.RoleDao;
import com.triple.o.labs.imageAnalizer.entities.Role;
import com.triple.o.labs.imageAnalizer.enums.RoleName;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public Set<Role> getSignupRoles(UserType userType) {
        Set<Role> roleSet = new HashSet<>();
        if (UserType.ADMIN == userType) {
            roleSet.add(roleDao.findByName(RoleName.ROLE_USER_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_USER_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_USER_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_USER_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_USER_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_ASSIGN));
        } else if (UserType.LAB == userType) {
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_DOCTOR_ASSIGN));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_ASSIGN));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_ASSIGN));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_PRINT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_UPLOAD));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_ASSIGN));
        } else if (UserType.DOCTOR == userType) {
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_PATIENT_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_LIST));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_EDIT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_CREATE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_DELETE));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_ASSIGN));
            roleSet.add(roleDao.findByName(RoleName.ROLE_CASES_PRINT));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_VIEW));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_UPLOAD));
            roleSet.add(roleDao.findByName(RoleName.ROLE_SCAN_IMAGES_ASSIGN));
        }

        return roleSet;
    }
}
