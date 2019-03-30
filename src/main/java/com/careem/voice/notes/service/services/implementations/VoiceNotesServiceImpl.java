package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.enums.VoiceNoteStatus;
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
    private JourneyRepository journeyRepository;

    @Autowired
    private CustomerAppServiceGateway customerAppServiceGateway;


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
                if(rider.getStillWaiting()) {
                    boolean voiceNoteSent = customerAppServiceGateway.sendVoiceNote(voiceNoteLink, rider.getCustomerId());
                    if (!voiceNoteSent) {
                        //TODO: create a scheduled function to send to failed customers
                    } else {
                        VoiceNoteRiderLog voiceNoteRiderLog = new VoiceNoteRiderLog(voiceNote, rider, false, false);
                        riderLogs.add(voiceNoteRiderLog);
                    }
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

    public VoiceNoteInfo getVoiceNoteInfo(String voiceNoteId, String journeyTrackingId) throws NotFoundException{
        VoiceNoteInfo voiceNoteInfo = new VoiceNoteInfo();
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if(journey != null) {
            VoiceNote voiceNote = null;
            if(journey.getVoiceNotes() != null){
                for (VoiceNote journeyVoiceNote: journey.getVoiceNotes()) {
                    if(voiceNote.getVoiceNoteExternalId().equals(voiceNoteId)){
                        voiceNote = journeyVoiceNote;
                    }
                }
                if(voiceNote != null){
                    List<String> ridersReceivedVoiceNote = new ArrayList<>();
                    List<String> ridersListenedToVoiceNote = new ArrayList<>();
                    if(voiceNote.getRiderLogs() != null) {
                        for (VoiceNoteRiderLog voiceNoteRiderLog : voiceNote.getRiderLogs()) {
                            if (voiceNoteRiderLog.getReceived())
                                ridersReceivedVoiceNote.add(voiceNoteRiderLog.getRider().getCustomerId());
                            if (voiceNoteRiderLog.getListened())
                                ridersListenedToVoiceNote.add(voiceNoteRiderLog.getRider().getCustomerId());
                        }
                    }

                    voiceNoteInfo.setCustomersListenedToVoiceNote(ridersListenedToVoiceNote);
                    voiceNoteInfo.setCustomersReceivedVoiceNote(ridersReceivedVoiceNote);
                    voiceNoteInfo.setVoiceNoteId(voiceNoteId);
                    return voiceNoteInfo;
                }
                else{
                    throw new NotFoundException("Voice Note with ID:" + voiceNoteId +" for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
                }
            }
            else{
                throw new NotFoundException("Voice Note with ID:" + voiceNoteId +" for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
            }
        }
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    public VoiceNoteRiderLog updateVoiceNoteStatus(VoiceNoteStatus voiceNoteStatus, String journeyTrackingId,
                                                 String voiceNoteId, String customerId) throws NotFoundException{
        VoiceNoteRiderLog voiceNoteRiderLog = null;
        int voiceNoteIndex = -1;
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        if (journey != null) {
            VoiceNote voiceNote = null;
            if (journey.getVoiceNotes() != null) {
                int index = 0;
                for (VoiceNote journeyVoiceNote : journey.getVoiceNotes()) {
                    if (voiceNote.getVoiceNoteExternalId().equals(voiceNoteId)) {
                        voiceNote = journeyVoiceNote;
                        voiceNoteIndex = index;
                    }
                    index++;
                }
                if (voiceNote != null) {
                    if (voiceNote.getRiderLogs() != null) {
                        for (VoiceNoteRiderLog journeyVoiceNoteRiderLog : voiceNote.getRiderLogs()) {
                            if(journeyVoiceNoteRiderLog.getRider().getCustomerId().equals(customerId)){
                                if(voiceNoteStatus.equals(VoiceNoteStatus.RECEIVED)){
                                    journeyVoiceNoteRiderLog.setReceived(true);
                                }
                                else if(voiceNoteStatus.equals(VoiceNoteStatus.LISTENED)){
                                    journeyVoiceNoteRiderLog.setReceived(true);
                                    journeyVoiceNoteRiderLog.setListened(true);
                                }
                                voiceNoteRiderLog = journeyVoiceNoteRiderLog;
                            }
                        }
                        if(voiceNoteRiderLog != null){
                            List<VoiceNote> voiceNotes = journey.getVoiceNotes();
                            voiceNotes.set(voiceNoteIndex, voiceNote);
                            journey.setVoiceNotes(voiceNotes);
                            journeyRepository.save(journey);
                            return voiceNoteRiderLog;
                        }
                        else{
                            throw new NotFoundException("Rider with ID:" + customerId + " is not associated with voice note with ID:" + voiceNoteId + ".");
                        }
                    }
                    else{
                        throw new NotFoundException("Rider with ID:" + customerId + " is not associated with voice note with ID:" + voiceNoteId + ".");
                    }
                } else {
                    throw new NotFoundException("Voice Note with ID:" + voiceNoteId + " for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
                }
            } else {
                throw new NotFoundException("Voice Note with ID:" + voiceNoteId + " for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
            }
        } else {
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

}
