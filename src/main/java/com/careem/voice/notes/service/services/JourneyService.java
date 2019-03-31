package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import javassist.NotFoundException;

public interface JourneyService {
    void startJourney(String journeyTrackingId);
    String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException;
    String updateRiderStatus(String journeyTrackingId , String riderId, RiderStatus riderStatus) throws NotFoundException;
}
