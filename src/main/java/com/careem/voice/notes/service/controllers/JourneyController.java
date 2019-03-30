package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.models.Journey;
import com.careem.voice.notes.service.services.JourneyService;
import com.careem.voice.notes.service.utils.ApiResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/journey")
public class JourneyController {

    @Autowired
    private JourneyService journeyService;

    @PostMapping("/{journeyTrackingId}/start")
    public ResponseEntity<ApiResponse<Journey>> startJourney(@PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                                             @RequestParam(value = "driverId") String driverId){
        Journey journey = journeyService.startJourney(journeyTrackingId, driverId);
        return new ResponseEntity(new ApiResponse(HttpStatus.CREATED, true,
                "Journey with id:" +journeyTrackingId + " is successfully started.", journey), HttpStatus.CREATED);
    }

    @PostMapping("/{journeyTrackingId}/subscribe")
    public ResponseEntity<String> subscribeToJourney(@RequestParam String  customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        return new ResponseEntity<>(journeyService.subscribeToJourney(journeyTrackingId, customerId), HttpStatus.OK);
    }

    @PostMapping("/{journeyTrackingId}/rider/{customerId}")
    public ResponseEntity<String> muteRiderFromJourney(@PathVariable(name = "riderId") String customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        return new ResponseEntity<>(journeyService.muteRiderFromJourney(journeyTrackingId, customerId), HttpStatus.OK);
    }

}
