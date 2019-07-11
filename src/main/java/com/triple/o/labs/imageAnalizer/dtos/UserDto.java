package com.triple.o.labs.imageAnalizer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String role;
}
