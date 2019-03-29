package com.careem.voice.notes.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Journey extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String driverId;

    String trackingId;

    @OneToMany(mappedBy = "journeyId")
    List<VoiceNote> voiceNotes;

    @OneToMany(mappedBy = "journeyId")
    List<Rider> riders;
}
