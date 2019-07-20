package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Role;
import com.triple.o.labs.imageAnalizer.enums.UserType;

import java.util.Set;

public interface RoleService {
    Set<Role> getSignupRoles(UserType userType);
}
