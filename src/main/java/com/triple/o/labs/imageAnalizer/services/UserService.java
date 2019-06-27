package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto addUser(UserDto user);
    UserDto getUser(Long id);
    UserDto deactivateUser(Long id);
}
