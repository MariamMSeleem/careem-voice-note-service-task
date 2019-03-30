package com.careem.voice.notes.service.models;

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
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String modifiedBy;
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;
    @Column(name = "last_modified_date")
    @LastModifiedDate
    private Date modifiedDate;
}
