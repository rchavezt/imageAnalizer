package com.triple.o.labs.imageAnalizer.entities;

import com.triple.o.labs.imageAnalizer.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleName name;
}
