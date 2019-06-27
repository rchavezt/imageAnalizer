package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UsersDao extends CrudRepository<User,Long> {
    List<User> findAll();
    User findByUsername(String username);
}
