package com.careem.voice.notes.service.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"journey_id", "customerId"})})
public class Rider extends BaseEntity {

    @ManyToOne
    private Journey journey;

    private String customerId;

    private Boolean stillWaiting;

    public Rider(String customerId, Boolean stillWaiting){
        this.customerId = customerId;
        this.stillWaiting = stillWaiting;
    }
}
