package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User addOrUpdateUser(UserDto user);
    User getUser(Long id);
    User deactivateUser(Long id);
}
