package com.careem.voice.notes.service.services.implementations;

import com.careem.voice.notes.service.models.dtos.VoiceNoteDto;
import com.careem.voice.notes.service.models.dtos.VoiceNoteInfo;
import com.careem.voice.notes.service.models.dtos.VoiceNoteRiderLogDto;
import com.careem.voice.notes.service.models.dtos.enums.VoiceNoteStatus;
import com.careem.voice.notes.service.models.entities.Journey;
import com.careem.voice.notes.service.models.entities.Rider;
import com.careem.voice.notes.service.models.entities.VoiceNote;
import com.careem.voice.notes.service.models.entities.VoiceNoteRiderLog;
import com.careem.voice.notes.service.models.entities.enums.RiderStatus;
import com.careem.voice.notes.service.models.repositories.JourneyRepository;
import com.careem.voice.notes.service.models.repositories.VoiceNoteRepository;
import com.careem.voice.notes.service.models.repositories.VoiceNoteRiderLogRepository;
import com.careem.voice.notes.service.services.VoiceNoteService;
import com.careem.voice.notes.service.services.customerAppGateways.CustomerAppServiceGateway;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*Implementation of the voice note service. */
@Service
public class VoiceNotesServiceImpl implements VoiceNoteService{
    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private VoiceNoteRepository voiceNoteRepository;

    @Autowired
    private VoiceNoteRiderLogRepository voiceNoteRiderLogRepository;

    @Autowired
    private CustomerAppServiceGateway customerAppServiceGateway;

    //send voice note to all the subscribed riders who are still waiting
    public VoiceNoteDto sendVoiceNote(String voiceNoteLink, String journeyTrackingId) throws NotFoundException{
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        //check if a journey exists with this tracking id
        if(journey != null) {
            List<VoiceNote> voiceNotes = journey.getVoiceNotes();
            //create a voice note entity and generate voice note id using the journey's tracking id
            String voiceNoteId = journey.getTrackingId()+ '_' + Integer.toString(voiceNotes.size()+1);
            VoiceNote voiceNote = new VoiceNote(voiceNoteId, voiceNoteLink);
            voiceNote.setJourney(journey);

            List<VoiceNoteRiderLog> riderLogs =  new ArrayList<>();
            // loop over all the subscribed riders, check if they are still waiting and send them the voice note through customer app API
            for (Rider rider: journey.getRiders()) {
                if(rider.getRiderStatus().equals(RiderStatus.WAITING)) {
                    //Send the voice note link
                    /*Note: An extra step would be to log if any customer's api call
                    failed and make a scheduled function to resend the voice note to all the failed customers */
                    boolean voiceNoteSent = customerAppServiceGateway.sendVoiceNote(voiceNoteLink, rider.getCustomerId(), voiceNoteId);
                   //After sending the voice note, create a log record and attach it to the voice note and save both
                    if (voiceNoteSent) {
                        VoiceNoteRiderLog voiceNoteRiderLog = new VoiceNoteRiderLog(voiceNote, rider, false, false);
                        riderLogs.add(voiceNoteRiderLog);
                        voiceNote.setRiderLogs(riderLogs);
                        voiceNotes.add(voiceNote);
                        voiceNoteRepository.save(voiceNote);
                        voiceNoteRiderLogRepository.save(voiceNoteRiderLog);
                    }
                }
            }
            //return the voice note external id
            return new VoiceNoteDto(voiceNoteId);
        }
        //if the journey doesn't exist, throw an exception
        else{
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    //retrieve voice note info, list of receivers and listeners
    public VoiceNoteInfo getVoiceNoteInfo(String voiceNoteId, String journeyTrackingId) throws NotFoundException {
        VoiceNoteInfo voiceNoteInfo = new VoiceNoteInfo();
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        //check if a journey exists with this tracking id
        if (journey != null) {
            VoiceNote voiceNote = null;
            //get the voice note entity of this journey
            for (VoiceNote journeyVoiceNote : journey.getVoiceNotes()) {
                if (journeyVoiceNote.getVoiceNoteExternalId().equals(voiceNoteId)) {
                    voiceNote = journeyVoiceNote;
                }
            }
            //check if the voice note exists
            if (voiceNote != null) {
                List<String> ridersReceivedVoiceNote = new ArrayList<>();
                List<String> ridersListenedToVoiceNote = new ArrayList<>();
                //get all the riders who received and/or listened to the voice note from the logs
                for (VoiceNoteRiderLog voiceNoteRiderLog : voiceNote.getRiderLogs()) {
                    if (voiceNoteRiderLog.getReceived())
                        ridersReceivedVoiceNote.add(voiceNoteRiderLog.getRider().getCustomerId());
                    if (voiceNoteRiderLog.getListened())
                        ridersListenedToVoiceNote.add(voiceNoteRiderLog.getRider().getCustomerId());
                }
                //create the voice note info dto and return it
                voiceNoteInfo.setCustomersListenedToVoiceNote(ridersListenedToVoiceNote);
                voiceNoteInfo.setCustomersReceivedVoiceNote(ridersReceivedVoiceNote);
                voiceNoteInfo.setVoiceNoteId(voiceNoteId);
                return voiceNoteInfo;
            } //if voice note doesn't exist, throw an exception
            else {
                throw new NotFoundException("Voice Note with ID:" + voiceNoteId + " for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
            }
        }//if the journey doesn't exist, throw an exception
        else {
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

    //update the voice note status sent to a certain rider, whether it's received or listened to by the rider
    public VoiceNoteRiderLogDto updateVoiceNoteStatus(VoiceNoteStatus voiceNoteStatus, String journeyTrackingId,
                                                 String voiceNoteId, String customerId) throws NotFoundException {
        VoiceNoteRiderLog voiceNoteRiderLog = null;
        int voiceNoteIndex = -1;
        Journey journey = journeyRepository.findByTrackingId(journeyTrackingId);
        //check if a journey exists with this tracking id
        if (journey != null) {
            VoiceNote voiceNote = null;
            int index = 0;
            //get the voice note entity of this journey and its index
            for (VoiceNote journeyVoiceNote : journey.getVoiceNotes()) {
                if (journeyVoiceNote.getVoiceNoteExternalId().equals(voiceNoteId)) {
                    voiceNote = journeyVoiceNote;
                    voiceNoteIndex = index;
                }
                index++;
            }
            //check if the voice note exists
            if (voiceNote != null) {
                //loop over the rider logs for this voice note, set the given rider log with the sent status
                for (VoiceNoteRiderLog journeyVoiceNoteRiderLog : voiceNote.getRiderLogs()) {
                    if (journeyVoiceNoteRiderLog.getRider().getCustomerId().equals(customerId)) {
                        if (voiceNoteStatus.equals(VoiceNoteStatus.RECEIVED)) {
                            journeyVoiceNoteRiderLog.setReceived(true);
                        }
                        //if a voice note is listened to, then it must have been received
                        else if (voiceNoteStatus.equals(VoiceNoteStatus.LISTENED)) {
                            journeyVoiceNoteRiderLog.setReceived(true);
                            journeyVoiceNoteRiderLog.setListened(true);
                        }
                        voiceNoteRiderLog = journeyVoiceNoteRiderLog;
                    }
                }
                //check if a log for this rider associated with this voice note exists
                if (voiceNoteRiderLog != null) {
                    //update the voice note with the updated logs and save
                    List<VoiceNote> voiceNotes = journey.getVoiceNotes();
                    voiceNotes.set(voiceNoteIndex, voiceNote);
                    journey.setVoiceNotes(voiceNotes);
                    journeyRepository.save(journey);
                    return new VoiceNoteRiderLogDto(voiceNoteRiderLog.getVoiceNote().getVoiceNoteExternalId(),
                            voiceNoteRiderLog.getRider().getCustomerId(), voiceNoteRiderLog.getReceived(), voiceNoteRiderLog.getListened());
                }
                //if a rider log doesn't exist, throw an exception
                else {
                    throw new NotFoundException("Rider with ID:" + customerId + " is not associated with voice note with ID:" + voiceNoteId + ".");
                }
            } //if voice note doesn't exist, throw an exception
            else {
                throw new NotFoundException("Voice Note with ID:" + voiceNoteId + " for Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
            }
        }//if the journey doesn't exist, throw an exception
        else {
            throw new NotFoundException("Journey with tracking ID: " + journeyTrackingId + " doesn't not exist.");
        }
    }

}
