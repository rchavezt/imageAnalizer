package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
public abstract class UserTrackingEntity extends CommonEntity{

    @Column
    private String createdBy;

    @Column
    private String updatedBy;
}
