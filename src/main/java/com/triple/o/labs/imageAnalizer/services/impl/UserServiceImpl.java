package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
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

    public List<User> getUsers() {
        List<User> userList = usersDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        }
        //FIXME
        //return userDtoList;
        return null;
    }

    @Override
    public User addOrUpdateUser(UserDto user) {
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
        //FIXME
        //return user;
        return null;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = usersDao.findById(id);
        return user.get();
    }


    @Override
    public User deactivateUser(Long id) {
        UserDto returnUser = new UserDto();
        Optional<User> userAsOptional = usersDao.findById(id);
        User user = userAsOptional.get();
        user.setActive(false);
        user = usersDao.save(user);
        BeanUtils.copyProperties(user, returnUser);
        //FIXME
        //return returnUser;
        return null;
    }

    @Override
    public List<User> getLaboratoryUsers() {
        return usersDao.findByUserLaboratory();
    }
}
