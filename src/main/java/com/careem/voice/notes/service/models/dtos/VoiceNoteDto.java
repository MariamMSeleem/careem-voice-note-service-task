package com.careem.voice.notes.service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*Dto for the response when the sendVoiceNote API is called.*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoiceNoteDto {
    String voiceNoteId;
}
