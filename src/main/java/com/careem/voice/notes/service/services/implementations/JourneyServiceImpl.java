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

/*Implementation of the Journey Service. */
@Service
public class JourneyServiceImpl implements JourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private RiderRepository riderRepository;

    //create a journey entity with the given tracking id when a journey starts
    public void createJourney(String journeyTrackingId){
        Journey journey = new Journey(journeyTrackingId);
        journeyRepository.save(journey);
    }

    //given a customer id, subscribe to the journey as a rider
    public String subscribeToJourney(String journeyTrackingId, String customerId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        //if a journey exists with this tracking id
        if(journey != null) {
            //create a new rider attached to the given journey, initial status of the rider is waiting
            Rider rider = new Rider(customerId, RiderStatus.WAITING);
            rider.setJourney(journey);
            riderRepository.save(rider);
            return "Rider successfully subscribed to Journey " + journeyTrackingId +".";
        }
        //throw an exception if the journey with the given tracking id didn't exist
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }



}
