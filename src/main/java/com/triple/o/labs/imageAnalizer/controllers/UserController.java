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
        return userService.getUsers();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
    public UserDto addUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/deactivateUser/{id}", method = RequestMethod.POST, produces = "application/json")
    public UserDto deactivateUser(@PathVariable Long id){
        return userService.deactivateUser(id);
    }


}
