package com.careem.voice.notes.service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/journey")
public class JourneyController {

    @PostMapping("/{journeyTrackingId}/start")
    public ResponseEntity<String> startJourney(@PathVariable(name = "journeyTrackingId") String journeyId){
        return new ResponseEntity<String>("Journey started with id: ", HttpStatus.CREATED);
    }

    @PostMapping("/{journeyTrackingId}/subscribe")
    public ResponseEntity<String> subscribeToJourney(@RequestBody String  customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyId){
        return new ResponseEntity<String>("Subscribed to journey with id:  successfully.", HttpStatus.OK);
    }

    @PostMapping("/{journeyTrackingId}/rider/{riderId}")
    public ResponseEntity<String> muteRiderFromJourney(@PathVariable(name = "riderId") String riderId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyId){
        return new ResponseEntity<String>("Rider was muted from journey with id: successfully.", HttpStatus.OK);
    }

}
