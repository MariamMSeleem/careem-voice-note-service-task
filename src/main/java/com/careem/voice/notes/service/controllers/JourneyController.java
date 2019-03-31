package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import com.careem.voice.notes.service.services.JourneyService;
import com.careem.voice.notes.service.controllers.utils.ApiResponse;
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

    @PostMapping("/{journeyTrackingId}/start/")
    public ResponseEntity<ApiResponse<String>> startJourney(@PathVariable(name = "journeyTrackingId") String journeyTrackingId){
        journeyService.startJourney(journeyTrackingId);
        return new ResponseEntity(new ApiResponse(HttpStatus.CREATED, true,
                "Journey with id:" +journeyTrackingId + " is successfully started.", null), HttpStatus.CREATED);
    }

    @PostMapping("/{journeyTrackingId}/subscribe/")
    public ResponseEntity<String> subscribeToJourney(@RequestParam(name = "customerId") String  customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        String confirmationMessage = journeyService.subscribeToJourney(journeyTrackingId, customerId);
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true, confirmationMessage, null), HttpStatus.OK);
    }

    @PatchMapping("/{journeyTrackingId}/rider/{customerId}/")
    public ResponseEntity<String> updateRiderStatus(@PathVariable(name = "customerId") String customerId,
                                                    @PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                                    @RequestParam(name = "riderStatus")RiderStatus riderStatus) throws NotFoundException{
        String confirmationMessage = journeyService.updateRiderStatus(journeyTrackingId, customerId, riderStatus);
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true, confirmationMessage, null), HttpStatus.OK);
    }


}
