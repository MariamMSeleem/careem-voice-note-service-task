package com.careem.voice.notes.service.services.customerAppGateways;

import org.springframework.stereotype.Service;


public interface CustomerAppServiceGateway {
    boolean sendVoiceNote(String voiceNoteLink, String customerId, String voiceNoteId);
}
