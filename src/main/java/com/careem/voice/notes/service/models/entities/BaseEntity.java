package com.careem.voice.notes.service.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 Auditing and id Base class
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "created_by")
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private Date createdDate;

    @Column(name = "last_modified_date")
    @LastModifiedDate
    @JsonIgnore
    private Date modifiedDate;
}
