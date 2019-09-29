package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.UpdateUserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersDao usersDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return usersDao.findAll();
    }

    @Override
    public User updateUser(User user, UpdateUserDto updateUserDto) {

        BeanUtils.copyProperties(updateUserDto, user, getNullPropertyNames(updateUserDto));

        if (updateUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }

        return usersDao.save(user);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = usersDao.findById(id);
        return user.get();
    }


    @Override
    public User deactivateUser(Long id) {
        User user = usersDao.findById(id).get();
        user.setActive(false);
        return usersDao.save(user);
    }

    @Override
    public List<User> getUsersbyType(UserType userType) {
        return usersDao.findByUserType(userType);
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
