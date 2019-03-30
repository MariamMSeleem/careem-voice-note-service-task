package com.careem.voice.notes.service.repositories;

import com.careem.voice.notes.service.models.VoiceNoteRiderLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceNoteRiderLogRepository extends CrudRepository<VoiceNoteRiderLog, Long> {



}
