package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.models.dtos.VoiceNoteDto;
import com.careem.voice.notes.service.models.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.models.dtos.VoiceNoteRiderLogDto;
import com.careem.voice.notes.service.models.dtos.enums.VoiceNoteStatus;
import com.careem.voice.notes.service.models.entities.VoiceNote;
import com.careem.voice.notes.service.services.VoiceNoteService;
import com.careem.voice.notes.service.controllers.utils.ApiResponse;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/journey/{journeyTrackingId}/voice-note/")
public class VoiceNotesController {

    @Autowired
    private VoiceNoteService voiceNoteService;

    /*This API is called by the captain app to broadcast a voice note to all the waiting riders.*/
    @ApiOperation(value = "Send a Voice Note", notes = "Sends voice notes from the captain to all the subscribed waiting riders.")
    @PostMapping("/send/")
    public ResponseEntity<VoiceNoteDto> sendVoiceNote(@RequestParam(name = "voiceNoteLink")  String  voiceNoteLink,
                                                   @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException {
        String confirmationMessage = "Voice note successfully sent to riders.";
        VoiceNoteDto voiceNoteDto =  voiceNoteService.sendVoiceNote(voiceNoteLink, journeyTrackingId);
        return new ResponseEntity(new ApiResponse(HttpStatus.OK, true, confirmationMessage, voiceNoteDto), HttpStatus.OK);
    }


    /*This API is called by the captain app to get the list of riders who received and/or listened to the voice note.*/
    @ApiOperation(value = "Get Voice Note Info", notes = "Allows captain to check which riders received or/and listened to a voice note.")
    @GetMapping("/{voiceNoteId}/info/")
    public ResponseEntity<VoiceNoteInfo> getVoiceNoteInfo(@PathVariable(name = "voiceNoteId") String voiceNoteId,
                                                          @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        VoiceNoteInfo voiceNoteInfo = voiceNoteService.getVoiceNoteInfo(voiceNoteId, journeyTrackingId);
        return  new ResponseEntity(new ApiResponse(HttpStatus.OK, true,null, voiceNoteInfo), HttpStatus.OK);
    }

    /*This API is called by the customer app when a customer received or when a customer listens to a voice note.*/
    @ApiOperation(value = "Update Voice Note Status", notes = "Allows a rider to update the service " +
            "about whether they received or listened to a certain voice note.")
    @PutMapping("/{voiceNoteId}/rider/{customerId}/status/")
    public ResponseEntity<VoiceNoteRiderLogDto> updateVoiceNoteStatus(@RequestParam(name = "voiceNoteStatus") VoiceNoteStatus voiceNoteStatus,
                                                                 @PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                                                 @PathVariable(name = "voiceNoteId") String voiceNoteId,
                                                                 @PathVariable(name = "customerId") String customerId) throws NotFoundException{
        VoiceNoteRiderLogDto voiceNoteRiderLog = voiceNoteService.updateVoiceNoteStatus(voiceNoteStatus, journeyTrackingId, voiceNoteId, customerId);
        return  new ResponseEntity(new ApiResponse(HttpStatus.OK, true,"Voice note status successfully updated.", voiceNoteRiderLog), HttpStatus.OK);

    }

}
