package com.careem.voice.notes.service.services.gateways.implementations;

import com.careem.voice.notes.service.services.gateways.CustomerAppServiceGateway;
import org.springframework.stereotype.Service;

@Service
public class CustomerAppServiceGatewayImpl implements CustomerAppServiceGateway {
    public boolean sendVoiceNote(String voiceNoteLink, String customerId){
        //TODO
        return true;
    }

}
