package com.careem.voice.notes.service.services.customerAppGateways.implementations;

import com.careem.voice.notes.service.services.customerAppGateways.CustomerAppServiceGateway;
import com.careem.voice.notes.service.services.customerAppGateways.dtos.VoiceNoteCustomerInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
* Gateway Service Implementation for sending the voice note link to the riders.
* */
@Log4j
@Service
public class CustomerAppServiceGatewayImpl implements CustomerAppServiceGateway {

    @Value("${customer.app.service.base.url}")
    private String customerAppServiceBaseURL;

    private RestTemplate restTemplate;

    /*Invoked once for each rider to send them the voice note by calling the customer's app API.*/
    public boolean sendVoiceNote(String voiceNoteLink, String customerId, String voiceNoteId){
        boolean liveService = false;

        //Demonstrates what will happen if the service was live (calling the API of the customer app)
        if(liveService) {
            //Formulate a dto to send  to the customer app's API
            VoiceNoteCustomerInfo voiceNoteCustomerInfo = new VoiceNoteCustomerInfo(customerId, voiceNoteId, voiceNoteLink);
            //Call the customer app's API
            HttpEntity<VoiceNoteCustomerInfo> voiceNoteCustomerInfoHttpEntity = new HttpEntity<>(voiceNoteCustomerInfo);
            restTemplate = new RestTemplate();
            try {
                restTemplate.exchange(customerAppServiceBaseURL, HttpMethod.POST, voiceNoteCustomerInfoHttpEntity, String.class);
            } catch (Exception e) {
                log.error("Error while sending Voice Note message, Exception " + e.getStackTrace());
                return false;
            }
        }
        //For the purpose of this task, always return true
        return true;
    }

}
