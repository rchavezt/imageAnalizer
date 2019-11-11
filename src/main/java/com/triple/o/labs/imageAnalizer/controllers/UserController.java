package com.triple.o.labs.imageAnalizer.controllers;


import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.daos.UsersDao;
import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.dtos.requests.UpdateUserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user", headers = "Authorization")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UsersDao usersDao;

    @Secured("ROLE_USER_LIST")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    public List<UserDto> getAllUsers(@CurrentUser UserPrincipal userPrincipal){
        User admin = userService.getUser(userPrincipal.getId());

        if (UserType.ADMIN != admin.getUserType()) {
            throw new BadRequestException("User must be ADMIN to check all users");
        }

        List<UserDto> resultList = new ArrayList<>();
        userService.getAllUsers().forEach(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            resultList.add(userDto);
        });

        return resultList;
    }

    @Secured("ROLE_USER_VIEW")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        User admin = userService.getUser(userPrincipal.getId());

        if (UserType.ADMIN != admin.getUserType()) {
            throw new BadRequestException("User must be ADMIN to check user");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userService.getUser(id), userDto);

        return userDto;
    }

    @Secured("ROLE_USER_EDIT")
    @RequestMapping(value = "/edit", method = RequestMethod.PUT, produces = "application/json")
    public UserDto updateUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UpdateUserDto updateUserDto) {
        if(updateUserDto.getEmail() != null && usersDao.existsByEmail(updateUserDto.getEmail())) {
            throw new BadRequestException("User already exists with that email");
        }

        User user = userService.getUser(userPrincipal.getId());
        user =userService.updateUser(user, updateUserDto);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;

    }

    @Secured("ROLE_USER_DELETE")
    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public UserDto deactivateUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        User admin = userService.getUser(userPrincipal.getId());

        if (UserType.ADMIN != admin.getUserType()) {
            throw new BadRequestException("User must be ADMIN to deactivate a user");
        }

        if (id == admin.getId()){
            throw new BadRequestException("ADMIN cannot be deactivated by himself");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userService.deactivateUser(id), userDto);
        return userDto;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json")
    public UserDto getActualUser(@CurrentUser UserPrincipal userPrincipal){
        User user = userService.getUser(userPrincipal.getId());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }


    @PreAuthorize("hasRole('ROLE_PATIENT_LIST') and hasRole('ROLE_DOCTOR_LIST') and hasRole('ROLE_USER_LIST')")
    @RequestMapping(value = "/getAll/{userType}", method = RequestMethod.GET, produces = "application/json")
    public List<UserDto> getAllByUserType(@CurrentUser UserPrincipal userPrincipal, @PathVariable UserType userType){
        User admin = userService.getUser(userPrincipal.getId());
        if (UserType.ADMIN != admin.getUserType()) {
            throw new BadRequestException("User must be ADMIN to get patients");
        }
        List<UserDto> userDtoList = new ArrayList<>();
        userService.getUsersbyType(userType).forEach(user -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtoList.add(userDto);
        });

        return userDtoList;
    }
}
