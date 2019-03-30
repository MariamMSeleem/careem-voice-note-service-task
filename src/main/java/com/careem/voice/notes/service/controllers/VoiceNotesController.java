package com.careem.voice.notes.service.controllers;

import com.careem.voice.notes.service.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.dtos.VoiceNoteRiderLogDto;
import com.careem.voice.notes.service.enums.VoiceNoteStatus;
import com.careem.voice.notes.service.models.VoiceNote;
import com.careem.voice.notes.service.models.VoiceNoteRiderLog;
import com.careem.voice.notes.service.services.VoiceNoteService;
import com.careem.voice.notes.service.utils.ApiResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/journey/{journeyTrackingId}/voice-note/")
public class VoiceNotesController {

    @Autowired
    private VoiceNoteService voiceNoteService;

    @PostMapping("/send")
    public ResponseEntity<String> sendVoiceNote(@RequestParam String  voiceNoteLink,
                                                @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException {
        return new ResponseEntity<String>(voiceNoteService.sendVoiceNote(voiceNoteLink, journeyTrackingId), HttpStatus.OK);
    }


    @GetMapping("/{voiceNoteId}/info")
    public ResponseEntity<VoiceNoteInfo> getVoiceNoteInfo(@PathVariable(name = "voiceNoteID") String voiceNoteId,
                                                          @PathVariable(name = "journeyTrackingId") String journeyTrackingId) throws NotFoundException{
        VoiceNoteInfo voiceNoteInfo = voiceNoteService.getVoiceNoteInfo(voiceNoteId, journeyTrackingId);
        return  new ResponseEntity(new ApiResponse(HttpStatus.OK, true,null, voiceNoteInfo), HttpStatus.OK);
    }

    @PutMapping("/{voiceNoteId}/rider/{customerId}/status")
    public ResponseEntity<VoiceNoteRiderLogDto> updateVoiceNoteStatus(@RequestParam VoiceNoteStatus voiceNoteStatus,
                                                                 @PathVariable(name = "journeyTrackingId") String journeyTrackingId,
                                                                 @PathVariable(name = "voiceNoteID") String voiceNoteId,
                                                                 @PathVariable(name = "customerId") String customerId) throws NotFoundException{

        VoiceNoteRiderLogDto voiceNoteRiderLog = voiceNoteService.updateVoiceNoteStatus(voiceNoteStatus, journeyTrackingId, voiceNoteId, customerId);
        return  new ResponseEntity(new ApiResponse(HttpStatus.OK, true,"Voice note status successfully updated.", voiceNoteRiderLog), HttpStatus.OK);

    }

}
