package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.entities.Journey;
import com.careem.voice.notes.service.models.entities.Rider;
import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import com.careem.voice.notes.service.models.repositories.JourneyRepository;
import com.careem.voice.notes.service.models.repositories.RiderRepository;
import com.careem.voice.notes.service.services.RiderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*Implementation of the rider service*/
@Service
public class RiderServiceImpl implements RiderService{


    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private RiderRepository riderRepository;

    /*Update the rider's status to ON_BOARD or WAITING.
    Must be called to prevent the rider from receiving further voice notes.
     */
    public String updateRiderStatus(String journeyTrackingId, String customerId, RiderStatus riderStatus) throws NotFoundException {
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        //check if a journey with this tracking id exists
        if(journey != null) {
            //search for the rider with the given customer id and update its status
            List<Rider> riders = journey.getRiders();
            Rider foundRider = null;
            for (Rider rider : riders) {
                if (rider.getCustomerId().equals(customerId)) {
                    rider.setRiderStatus(riderStatus);
                    foundRider = rider;
                    break;
                }
            }
            //if a rider was found, save it with the new updated status
            if (foundRider != null) {
                riderRepository.save(foundRider);
                return "Rider with ID:"+ customerId + " from Journey " + journeyTrackingId + " is now " + riderStatus.getFullName() + ".";
            }
            //if a rider was not found, throw exception
            else{
                throw new NotFoundException("Rider with ID: " + customerId + " doesn't not exist in " +
                        "journey with tracking ID: " + customerId + ".");
            }
        }
        //if a journey wasn't find, throw an exception
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }

    }

}
