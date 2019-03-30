package com.careem.voice.notes.service.services;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

public interface VoiceNoteService {
    String sendVoiceNote(String voiceNoteLink, String journeyId) throws NotFoundException;
    String getVoiceNoteInfo(String voiceNoteId, String journeyId);
    String sendVoiceNoteStatus(String voiceNoteStatus, String journeyId, String voiceNoteId, String riderId);
}
