package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.UpdateUserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User updateUser(User user, UpdateUserDto updateUserDto);
    User getUser(Long id);
    User deactivateUser(Long id);
    List<User> getUsersbyType(UserType userType);
}
