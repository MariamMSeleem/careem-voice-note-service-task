package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.Journey;
import com.careem.voice.notes.service.models.Rider;
import com.careem.voice.notes.service.repositories.JourneyRepository;
import com.careem.voice.notes.service.services.JourneyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    public Journey startJourney(String journeyTrackingId, String driverId){
        Journey journey = new Journey(journeyTrackingId, driverId);
        return journeyRepository.save(journey);
    }

    public String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<Rider> riders = journey.getRiders();
            if(riders == null)
                riders = new ArrayList<>();
            Rider rider = new Rider(customerId, true);
            rider.setJourney(journey);
            riders.add(rider);
            journey.setRiders(riders);
            journeyRepository.save(journey);
            return "Rider successfully subscribed to Journey " + journeyTrackingId +".";
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    public String muteRiderFromJourney(String journeyTrackingId, String customerId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<Rider> riders = journey.getRiders();
            boolean riderFound = false;
            if(riders != null && riders.size() > 0) {
                for (Rider rider : riders) {
                    if (rider.getCustomerId().equals(customerId)) {
                        rider.setStillWaiting(false);
                        riderFound = true;
                        break;
                    }
                }
                if (riderFound) {
                    journey.setRiders(riders);
                    journeyRepository.save(journey);
                    return "Rider is muted from Journey " + journeyTrackingId + ".";
                }
                else{
                    throw new NotFoundException("Rider with ID: " + customerId + " doesn't not exist in " +
                            "journey with tracking ID: " + customerId + ".");
                }
            }
            else{
                throw new NotFoundException("Rider with ID: " + customerId + " doesn't not exist in " +
                        "journey with tracking ID: " + customerId + ".");
            }
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }

    }


}
