package com.careem.voice.notes.service.services.customerAppGateways.implementations;

import com.careem.voice.notes.service.services.customerAppGateways.CustomerAppServiceGateway;
import com.careem.voice.notes.service.services.customerAppGateways.VoiceNoteCustomerInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j
@Service
public class CustomerAppServiceGatewayImpl implements CustomerAppServiceGateway {

    @Value("${customer.app.service.base.url}")
    private String customerAppServiceBaseURL;

    private RestTemplate restTemplate;

    public boolean sendVoiceNote(String voiceNoteLink, String customerId, String voiceNoteId){
        boolean liveService = false;
        if(liveService) {
            VoiceNoteCustomerInfo voiceNoteCustomerInfo = new VoiceNoteCustomerInfo(customerId, voiceNoteId, voiceNoteLink);
            HttpEntity<VoiceNoteCustomerInfo> voiceNoteCustomerInfoHttpEntity = new HttpEntity<>(voiceNoteCustomerInfo);
            restTemplate = new RestTemplate();
            try {
                restTemplate.exchange(customerAppServiceBaseURL, HttpMethod.POST, voiceNoteCustomerInfoHttpEntity, String.class);
            } catch (Exception e) {
                log.error("Error while sending Voice Note message, Exception " + e.getStackTrace());
                return false;
            }
        }
        return true;
    }

}
