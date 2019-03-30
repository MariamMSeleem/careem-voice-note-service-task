package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.Journey;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

public interface JourneyService {
    Journey startJourney(String journeyTrackingId, String driverId);
    String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException;
    String muteRiderFromJourney(String journeyTrackingId , String riderId) throws NotFoundException;
}