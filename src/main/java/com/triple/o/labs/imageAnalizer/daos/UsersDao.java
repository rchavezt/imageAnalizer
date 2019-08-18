package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UsersDao extends CrudRepository<User,Long> {
    List<User> findAll();
    User findByUsername(String username);
    User findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("from User where userType = com.triple.o.labs.imageAnalizer.enums.UserType.LAB")
    List<User> findByUserLaboratory();
}
