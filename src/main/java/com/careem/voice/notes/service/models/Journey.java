package com.careem.voice.notes.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "trackingId", columnList = "trackingId", unique = true)})
public class Journey extends BaseEntity {

    private String trackingId;

    @OneToMany(mappedBy = "journey")
    private List<VoiceNote> voiceNotes;

    @OneToMany(mappedBy = "journey")
    private List<Rider> riders;

    public Journey(String trackingId){
        this.trackingId = trackingId;
    }
}
