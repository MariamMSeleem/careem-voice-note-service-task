package com.careem.voice.notes.service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*Dto for the response when the updateVoiceNoteStatus API is called.*/
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
