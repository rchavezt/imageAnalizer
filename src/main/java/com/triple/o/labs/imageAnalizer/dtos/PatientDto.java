package com.triple.o.labs.imageAnalizer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDto {
    private Long id;
    private String title;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
}
