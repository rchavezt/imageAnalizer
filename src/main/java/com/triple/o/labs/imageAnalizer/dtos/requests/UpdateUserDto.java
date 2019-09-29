package com.triple.o.labs.imageAnalizer.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    private String password;
    private String name;
    private String email;
}
