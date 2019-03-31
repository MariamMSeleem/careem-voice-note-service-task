package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.entities.Journey;
import com.careem.voice.notes.service.models.entities.Rider;
import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import com.careem.voice.notes.service.models.repositories.JourneyRepository;
import com.careem.voice.notes.service.models.repositories.RiderRepository;
import com.careem.voice.notes.service.services.JourneyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private RiderRepository riderRepository;

    public void startJourney(String journeyTrackingId){
        Journey journey = new Journey(journeyTrackingId);
        journeyRepository.save(journey);
    }

    public String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            Rider rider = new Rider(customerId, RiderStatus.WAITING);
            rider.setJourney(journey);
            riderRepository.save(rider);
            return "Rider successfully subscribed to Journey " + journeyTrackingId +".";
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    public String updateRiderStatus(String journeyTrackingId, String customerId, RiderStatus riderStatus) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<Rider> riders = journey.getRiders();
            Rider foundRider = null;
                for (Rider rider : riders) {
                    if (rider.getCustomerId().equals(customerId)) {
                        rider.setRiderStatus(riderStatus);
                        foundRider = rider;
                        break;
                    }
                }
                if (foundRider != null) {
                    riderRepository.save(foundRider);
                    return "Rider with ID:"+ customerId + " from Journey " + journeyTrackingId + " is now " + riderStatus.getFullName() + ".";
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
