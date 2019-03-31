package com.careem.voice.notes.service.services;

import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import javassist.NotFoundException;

/*Service interface used in the rider controller
for a driver to update the status of a rider to indicate that the rider boarded the vehicle
 */
public interface RiderService {
    String updateRiderStatus(String journeyTrackingId , String riderId, RiderStatus riderStatus) throws NotFoundException;
}
