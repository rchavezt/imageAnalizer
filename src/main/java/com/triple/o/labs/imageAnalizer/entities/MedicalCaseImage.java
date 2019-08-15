package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "ACTIVE = 1")
public class MedicalCaseImage extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private byte[] base64image;

}
