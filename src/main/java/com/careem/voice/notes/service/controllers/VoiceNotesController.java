package com.careem.voice.notes.service.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/journey/{journeyTrackingId}/voice-note/")
public class VoiceNotesController {

    @PostMapping("/send")
    public ResponseEntity<String> sendVoiceNote(@RequestBody String  voiceNoteLink,
                                                       @PathVariable(name = "journeyId") String journeyId){
        return new ResponseEntity<String>("Voice Note Sent Successfully", HttpStatus.OK);
    }


    @GetMapping("/{voiceNoteId}/info")
    public ResponseEntity<String> getVoiceNoteInfo(@PathVariable(name = "voiceNoteID") String voiceNoteId,
                                                   @PathVariable(name = "journeyId") String journeyId){
        return new ResponseEntity<String>("List of riders ids who listened or received ", HttpStatus.OK);
    }

    @PostMapping("/{voiceNoteId}/rider/{riderId}/status")
    public ResponseEntity<String> sendVoiceNoteStatus(@RequestBody String  voiceNoteStatus,
                                                      @PathVariable(name = "journeyId") String journeyId,
                                                      @PathVariable(name = "voiceNoteID") String voiceNoteId,
                                                      @PathVariable(name = "riderId") String riderId){
        return new ResponseEntity<String>("Send voice note status, received or listened", HttpStatus.OK);
    }

}
