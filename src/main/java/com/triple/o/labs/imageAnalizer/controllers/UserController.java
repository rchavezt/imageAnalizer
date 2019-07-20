package com.triple.o.labs.imageAnalizer.controllers;


import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", headers = "Authorization")
public class UserController {

    @Autowired
    UserService userService;

    @Secured("ROLE_USER_LIST")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    public List<UserDto> getAllUsers(){
        //FIXME
        //return userService.getUsers();
        return null;
    }

    @Secured("ROLE_USER_VIEW")
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUser(@PathVariable Long id){
        //FIXME
        //return userService.getUser(id);
        return null;
    }

    @Secured("ROLE_USER_CREATE")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public UserDto addOrUpdateUser(@RequestBody UserDto user) {
        //FIXME
        //return userService.addOrUpdateUser(user);
        return null;
    }

    @Secured("ROLE_USER_DELETE")
    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.POST, produces = "application/json")
    public UserDto deactivateUser(@PathVariable Long id){
        //FIXME
        //return userService.deactivateUser(id);
        return null;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json")
    public UserDto getActualUser(@CurrentUser UserPrincipal userPrincipal){
        User user = userService.getUser(userPrincipal.getId());
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }


}
