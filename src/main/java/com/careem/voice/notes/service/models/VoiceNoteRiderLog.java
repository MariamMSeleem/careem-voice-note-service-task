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
public class VoiceNoteRiderLog extends BaseEntity {

    @ManyToOne
    private VoiceNote voiceNote;

    private Rider rider;

    private Boolean received;

    private Boolean listened;
}
