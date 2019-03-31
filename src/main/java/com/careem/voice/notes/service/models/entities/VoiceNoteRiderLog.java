package com.careem.voice.notes.service.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * A Log for saving the status of the voice note sent
 * for each of the riders in the journey. i.e. whether it is received or listened to
 */
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
