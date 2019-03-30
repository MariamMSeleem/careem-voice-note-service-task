package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.Journey;
import com.careem.voice.notes.service.models.Rider;
import com.careem.voice.notes.service.repositories.JourneyRepository;
import com.careem.voice.notes.service.services.JourneyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;

public class JourneyServiceImpl implements JourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    public String startJourney(String journeyTrackingId, String driverId){
        Journey journey = new Journey(journeyTrackingId, driverId);
        journeyRepository.save(journey);
        return "Journey with id:" +journeyTrackingId + "is successfully started.";
    }

    public String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<Rider> riders = journey.getRiders();
            if(riders == null)
                riders = new ArrayList<>();
            int riderId = journey.getRiders().size()+1;
            Rider rider = new Rider(riderId, customerId, true);
            rider.setJourney(journey);
            riders.add(rider);
            journey.setRiders(riders);
            journeyRepository.save(journey);
            return "Customer successfully subscribed to Journey " + journeyTrackingId +".";
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    public String muteRiderFromJourney(String journeyTrackingId, String riderId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<Rider> riders = journey.getRiders();
            boolean riderFound = false;
            if(riders == null || riders.size() == 0) {
                throw new NotFoundException("Rider with ID: " + riderId + " doesn't not exist in " +
                        "journey with tracking ID: " + riderId + ".");
            }
            for (Rider rider: riders) {
                if(rider.getRiderId() == Integer.parseInt(riderId)){
                    rider.setStillWaiting(false);
                    riderFound = true;
                    break;
                }
            }
            if(!riderFound) {
                throw new NotFoundException("Rider with ID: " + riderId + " doesn't not exist in " +
                        "journey with tracking ID: " + riderId + ".");
            }
            else {
                journey.setRiders(riders);
                journeyRepository.save(journey);
                return "Customer successfully subscribed to Journey " + journeyTrackingId + ".";
            }
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }

    }


}
