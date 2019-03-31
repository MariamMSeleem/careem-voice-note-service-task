package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.controllers.utils.ApiResponse;
import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import com.careem.voice.notes.service.services.RiderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/journey/{journeyTrackingId}/rider/")
public class RiderController {
    @Autowired
    RiderService riderService;

    /* This API is called when a rider boards the vehicle as to stop sending the rider any further voice notes.*/
    @PatchMapping("/rider/{customerId}/update-status")
    public ResponseEntity<String> updateRiderStatus(@PathVariable(name = "customerId") String customerId,
                                                    @PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                                    @RequestParam(name = "riderStatus")RiderStatus riderStatus) throws NotFoundException {
        String confirmationMessage = riderService.updateRiderStatus(journeyTrackingId, customerId, riderStatus);
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true, confirmationMessage, null), HttpStatus.OK);
    }

}
