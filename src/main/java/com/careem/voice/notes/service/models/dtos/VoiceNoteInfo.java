package com.careem.voice.notes.service.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/*Dto for the response when the getVoiceNoteInfo API is called.*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoiceNoteInfo {
    String voiceNoteId;
    List<String> customersReceivedVoiceNote;
    List<String> customersListenedToVoiceNote;
}
