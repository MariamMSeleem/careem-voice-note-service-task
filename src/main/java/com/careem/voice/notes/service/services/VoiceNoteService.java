package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.dtos.VoiceNoteRiderLogDto;
import com.careem.voice.notes.service.enums.VoiceNoteStatus;
import javassist.NotFoundException;

public interface VoiceNoteService {
    String sendVoiceNote(String voiceNoteLink, String journeyTrackingId) throws NotFoundException;
    VoiceNoteInfo getVoiceNoteInfo(String voiceNoteId, String journeyTrackingId)throws NotFoundException;
    VoiceNoteRiderLogDto updateVoiceNoteStatus(VoiceNoteStatus voiceNoteStatus, String journeyTrackingId,
                                               String voiceNoteId, String customerId) throws NotFoundException;
}
