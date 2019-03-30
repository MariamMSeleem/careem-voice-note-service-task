package com.careem.voice.notes.service.services;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface JourneyService {
    String startJourney(String journeyTrackingId, String driverId);
    String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException;
    String muteRiderFromJourney(String journeyTrackingId , String riderId) throws NotFoundException;
}
