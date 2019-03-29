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
public class VoiceNote extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String voiceNoteExternalId;

    Long journeyId;

    String link;

    @OneToMany(mappedBy = "voiceNoteId")
    List<VoiceNoteCustomerLog> customerLogs;

}
