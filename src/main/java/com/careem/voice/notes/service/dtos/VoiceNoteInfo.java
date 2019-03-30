package com.careem.voice.notes.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoiceNoteInfo {
    String voiceNoteId;
    List<String> customersReceivedVoiceNote;
    List<String> customersListenedToVoiceNote;
}
