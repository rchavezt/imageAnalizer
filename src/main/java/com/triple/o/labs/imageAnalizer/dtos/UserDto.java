package com.triple.o.labs.imageAnalizer.dtos;

import com.triple.o.labs.imageAnalizer.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private UserType userType;
}
