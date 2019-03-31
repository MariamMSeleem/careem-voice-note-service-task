package com.careem.voice.notes.service.models.repositories;

import com.careem.voice.notes.service.models.entities.VoiceNoteRiderLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceNoteRiderLogRepository extends CrudRepository<VoiceNoteRiderLog, Long> {



}
