package com.triple.o.labs.imageAnalizer.entities;

import com.triple.o.labs.imageAnalizer.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "ACTIVE = 1")
public class User extends UserTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String name;

    @Column
    String email;

    @Enumerated(EnumType.STRING)
    @Column
    UserType userType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany
    private Set<Patient> patients;

}
