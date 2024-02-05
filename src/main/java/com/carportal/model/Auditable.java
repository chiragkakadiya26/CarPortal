package com.carportal.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
public class Auditable {

    public Date creationDate;
    public Date lastModifiedDate;

    @PrePersist
    public void onCreate() {
        this.creationDate = new Date();
        this.onUpdate();
    }

    @PreUpdate
    public void onUpdate() {
        this.lastModifiedDate = new Date();
    }
}
