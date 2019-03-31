package com.careem.voice.notes.service.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*Journey Entity that riders will subscribe to and many voice notes will be sent in one journey.*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "trackingId", columnList = "trackingId", unique = true)})
public class Journey extends BaseEntity {

    private String trackingId;

    @OneToMany(mappedBy = "journey")
    private List<VoiceNote> voiceNotes = new ArrayList<>();

    @OneToMany(mappedBy = "journey")
    private List<Rider> riders = new ArrayList<>();

    public Journey(String trackingId){
        this.trackingId = trackingId;
    }
}
