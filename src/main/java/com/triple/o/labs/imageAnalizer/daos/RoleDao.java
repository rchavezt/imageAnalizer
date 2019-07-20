package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.Role;
import com.triple.o.labs.imageAnalizer.enums.RoleName;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
