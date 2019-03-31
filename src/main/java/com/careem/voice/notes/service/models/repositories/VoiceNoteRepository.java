package com.careem.voice.notes.service.models.repositories;

import com.careem.voice.notes.service.models.entities.VoiceNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceNoteRepository extends CrudRepository<VoiceNote,Long> {
}
