package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.services.JourneyService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/journey")
public class JourneyController {

    @Autowired
    JourneyService journeyService;

    @PostMapping("/{journeyTrackingId}/start")
    public ResponseEntity<String> startJourney(@PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                               @Param(value = "driverId") String driverId){
        return new ResponseEntity<>(journeyService.startJourney(journeyTrackingId, driverId), HttpStatus.CREATED);
    }

    @PostMapping("/{journeyTrackingId}/subscribe")
    public ResponseEntity<String> subscribeToJourney(@RequestBody String  customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        return new ResponseEntity<>(journeyService.subscribeToJourney(journeyTrackingId, customerId), HttpStatus.OK);
    }

    @PostMapping("/{journeyTrackingId}/rider/{riderId}")
    public ResponseEntity<String> muteRiderFromJourney(@PathVariable(name = "riderId") String riderId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        return new ResponseEntity<>(journeyService.muteRiderFromJourney(journeyTrackingId, riderId), HttpStatus.OK);
    }

}
