package com.careem.voice.notes.service.models.entities;


import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/*
Rider entity will store the customerId (retrieved from another service, either the customer app or the journey handling service)
rider status (which is initially STILL_WAITING)
and a reference to the journey the rider is subscribed to.

 */
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
