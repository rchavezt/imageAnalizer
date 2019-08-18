package com.triple.o.labs.imageAnalizer.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class CommonEntity {

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column
    private Long dateCreated;

    @Column
    private Long dateUpdated;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dateCreated = timestamp.getTime();
        dateUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dateUpdated = timestamp.getTime();
    }
}