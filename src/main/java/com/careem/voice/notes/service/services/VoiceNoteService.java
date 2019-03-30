package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.enums.VoiceNoteStatus;
import com.careem.voice.notes.service.models.VoiceNoteRiderLog;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

public interface VoiceNoteService {
    String sendVoiceNote(String voiceNoteLink, String journeyTrackingId) throws NotFoundException;
    VoiceNoteInfo getVoiceNoteInfo(String voiceNoteId, String journeyTrackingId)throws NotFoundException;
    VoiceNoteRiderLog updateVoiceNoteStatus(VoiceNoteStatus voiceNoteStatus, String journeyTrackingId,
                                          String voiceNoteId, String customerId) throws NotFoundException;
}
