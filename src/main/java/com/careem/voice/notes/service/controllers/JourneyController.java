package com.careem.voice.notes.service.controllers;

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

    /* When a Journey is started, this API must be called to create
    a Journey Entity for voice notes handling
    * */
    @PostMapping("/{journeyTrackingId}/create/")
    public ResponseEntity<ApiResponse<String>> createJourney(@PathVariable(name = "journeyTrackingId") String journeyTrackingId){
        journeyService.createJourney(journeyTrackingId);
        return new ResponseEntity(new ApiResponse(HttpStatus.CREATED, true,
                "Journey with id:" +journeyTrackingId + " is successfully created.", null), HttpStatus.CREATED);
    }

    /*This API must be called when a new rider subscribes to a journey so that they can recieve voice notes.*/
    @PostMapping("/{journeyTrackingId}/subscribe/")
    public ResponseEntity<String> subscribeToJourney(@RequestParam(name = "customerId") String  customerId,
                                                     @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        String confirmationMessage = journeyService.subscribeToJourney(journeyTrackingId, customerId);
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true, confirmationMessage, null), HttpStatus.OK);
    }


}
