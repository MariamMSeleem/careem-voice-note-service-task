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
        @UniqueConstraint(columnNames = {"voice_note_id", "rider_id"})})
public class VoiceNoteRiderLog extends BaseEntity {

    @ManyToOne
    private VoiceNote voiceNote;

    @ManyToOne
    private Rider rider;

    private Boolean received;

    private Boolean listened;
}
