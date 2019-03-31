package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.dtos.VoiceNoteDto;
import com.careem.voice.notes.service.models.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.models.dtos.VoiceNoteRiderLogDto;
import com.careem.voice.notes.service.models.dtos.enums.VoiceNoteStatus;
import javassist.NotFoundException;

public interface VoiceNoteService {
    VoiceNoteDto sendVoiceNote(String voiceNoteLink, String journeyTrackingId) throws NotFoundException;
    VoiceNoteInfo getVoiceNoteInfo(String voiceNoteId, String journeyTrackingId)throws NotFoundException;
    VoiceNoteRiderLogDto updateVoiceNoteStatus(VoiceNoteStatus voiceNoteStatus, String journeyTrackingId,
                                               String voiceNoteId, String customerId) throws NotFoundException;
}
