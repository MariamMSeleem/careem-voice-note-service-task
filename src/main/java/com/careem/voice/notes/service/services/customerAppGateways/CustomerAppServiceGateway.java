package com.careem.voice.notes.service.services.customerAppGateways;

import org.springframework.stereotype.Service;


/*
* Gateway Service Implementation for sending the voice note link to the riders.
* */
public interface CustomerAppServiceGateway {
    boolean sendVoiceNote(String voiceNoteLink, String customerId, String voiceNoteId);
}
