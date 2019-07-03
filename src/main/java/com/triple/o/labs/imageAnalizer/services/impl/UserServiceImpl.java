package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersDao usersDao;

    public List<UserDto> getUsers() {
        List<User> userList = usersDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Override
    public UserDto addOrUpdateUser(UserDto user) {
        User newUser;
        try {
             Optional<User> existingUser = usersDao.findById(user.getId());
             newUser = existingUser.get();
        } catch (Exception e) {
            //TODO:Add log
            newUser = new User();
        }

        BeanUtils.copyProperties(user, newUser);
        newUser = usersDao.save(newUser);
        BeanUtils.copyProperties(newUser,user);
        return user;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = usersDao.findById(id);
        UserDto returnUser = new UserDto();
        BeanUtils.copyProperties(user.get(), returnUser);
        return returnUser;
    }


    @Override
    public UserDto deactivateUser(Long id) {
        UserDto returnUser = new UserDto();
        Optional<User> userAsOptional = usersDao.findById(id);
        User user = userAsOptional.get();
        user.setActive(false);
        user = usersDao.save(user);
        BeanUtils.copyProperties(user, returnUser);
        return returnUser;
    }
}
