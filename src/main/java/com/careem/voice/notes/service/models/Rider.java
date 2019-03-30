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
public class Rider extends BaseEntity {

    @ManyToOne
    private Journey journey;

    private Integer riderId;

    private String customerId;

    private Boolean stillWaiting;

    public Rider(Integer riderId, String customerId, Boolean stillWaiting){
        this.riderId = riderId;
        this.customerId = customerId;
        this.stillWaiting = stillWaiting;
    }
}
