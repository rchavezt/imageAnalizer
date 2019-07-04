package com.triple.o.labs.imageAnalizer.controllers;


import com.triple.o.labs.imageAnalizer.dtos.UserDto;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    public List<UserDto> getAllUsers(){
        //FIXME
        //return userService.getUsers();
        return null;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUser(@PathVariable Long id){
        //FIXME
        //return userService.getUser(id);
        return null;
    }

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST, produces = "application/json")
    public UserDto addOrUpdateUser(@RequestBody UserDto user) {
        //FIXME
        //return userService.addOrUpdateUser(user);
        return null;
    }

    @RequestMapping(value = "/deactivate/{id}", method = RequestMethod.POST, produces = "application/json")
    public UserDto deactivateUser(@PathVariable Long id){
        //FIXME
        //return userService.deactivateUser(id);
        return null;
    }


}
