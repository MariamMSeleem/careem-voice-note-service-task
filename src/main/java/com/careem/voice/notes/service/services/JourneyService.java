package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import javassist.NotFoundException;

/* Service Interface used in the Journey Controller
used to start a journey,
and for a rider to the subscribe to a journey.
 */
public interface JourneyService {
    void createJourney(String journeyTrackingId);
    String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException;
}
