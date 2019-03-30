package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.Journey;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

public interface JourneyService {
    void startJourney(String journeyTrackingId);
    String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException;
    String muteRiderFromJourney(String journeyTrackingId , String riderId) throws NotFoundException;
}
