package com.careem.voice.notes.service.models.entities;


import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
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

    @Enumerated(value = EnumType.STRING)
    private RiderStatus riderStatus;

    public Rider(String customerId, RiderStatus riderStatus){
        this.customerId = customerId;
        this.riderStatus = riderStatus;
    }
}
