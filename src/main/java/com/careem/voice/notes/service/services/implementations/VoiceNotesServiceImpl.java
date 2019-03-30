package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.Journey;
import com.careem.voice.notes.service.models.Rider;
import com.careem.voice.notes.service.models.VoiceNote;
import com.careem.voice.notes.service.models.VoiceNoteRiderLog;
import com.careem.voice.notes.service.repositories.JourneyRepository;
import com.careem.voice.notes.service.repositories.VoiceNoteRepository;
import com.careem.voice.notes.service.services.VoiceNoteService;
import com.careem.voice.notes.service.services.gateways.CustomerAppServiceGateway;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service

public class VoiceNotesServiceImpl implements VoiceNoteService{
    @Autowired
    JourneyRepository journeyRepository;

    @Autowired
    CustomerAppServiceGateway customerAppServiceGateway;


    @Transactional
    public String sendVoiceNote(String voiceNoteLink, String journeyTrackingId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            List<VoiceNote> voiceNotes = journey.getVoiceNotes();
            if(voiceNotes == null)
                voiceNotes = new ArrayList<>();
            String voiceNoteId = journey.getTrackingId()+ '_' + Integer.toString(voiceNotes.size()+1);
            VoiceNote voiceNote = new VoiceNote(voiceNoteId, voiceNoteLink);

            List<VoiceNoteRiderLog> riderLogs =  new ArrayList<>();
            for (Rider rider: journey.getRiders()) {
                boolean voiceNoteSent = customerAppServiceGateway.sendVoiceNote(voiceNoteLink, rider.getCustomerId());
                if(!voiceNoteSent) {
                    //TODO: create a scheduled function to send to failed customers
                }
                else{
                    VoiceNoteRiderLog voiceNoteRiderLog = new VoiceNoteRiderLog(voiceNote, rider, false, false);
                    riderLogs.add(voiceNoteRiderLog);
                }
            }
            voiceNote.setRiderLogs(riderLogs);

            voiceNotes.add(voiceNote);
            journey.setVoiceNotes(voiceNotes);
            journeyRepository.save(journey);
            return "Voice note successfully sent to riders.";
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    public String getVoiceNoteInfo(String voiceNoteId, String journeyId){
        return "";
    }

    public String sendVoiceNoteStatus(String voiceNoteStatus, String journeyId, String voiceNoteId, String riderId){
        return "";
    }
}
