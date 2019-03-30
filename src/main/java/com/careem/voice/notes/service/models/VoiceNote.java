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
public class VoiceNote extends BaseEntity {

    private String voiceNoteExternalId;

    @ManyToOne
    private Journey journey;

    private String link;

    @OneToMany(mappedBy = "voiceNote")
    private List<VoiceNoteRiderLog> riderLogs;

    public VoiceNote(String voiceNoteExternalId, String link){
        this.voiceNoteExternalId = voiceNoteExternalId;
        this.link = link;
    }

}
