package com.careem.voice.notes.service.services.customerAppGateways.dtos;

import lombok.AllArgsConstructor;


/*Dto to send to the customer app API when sending the voice note*/
@AllArgsConstructor
public class VoiceNoteCustomerInfo {
    String customerId;
    String voiceNoteId;
    String voiceNoteLink;
}
