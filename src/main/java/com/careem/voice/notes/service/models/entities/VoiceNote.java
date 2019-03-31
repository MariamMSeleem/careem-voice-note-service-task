package com.careem.voice.notes.service.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/*
Voice Note Entity, it stores the link for the voice note (which could be Amazon S3)
and the external ID whee other services will communicate with this service using it
and a reference to the journey each voice note is sent during.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VoiceNote extends BaseEntity {

    private String voiceNoteExternalId;

    @ManyToOne
    private Journey journey;

    private String link;

    @OneToMany(mappedBy = "voiceNote")
    private List<VoiceNoteRiderLog> riderLogs = new ArrayList<>();

    public VoiceNote(String voiceNoteExternalId, String link){
        this.voiceNoteExternalId = voiceNoteExternalId;
        this.link = link;
    }

}
