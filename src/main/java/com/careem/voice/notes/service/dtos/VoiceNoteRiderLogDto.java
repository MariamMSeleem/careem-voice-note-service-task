package com.careem.voice.notes.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoiceNoteRiderLogDto {
    private String voiceNoteId;

    private String riderId;

    private Boolean received;

    private Boolean listened;
}
