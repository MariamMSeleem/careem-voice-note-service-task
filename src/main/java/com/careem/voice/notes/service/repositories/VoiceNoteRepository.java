package com.careem.voice.notes.service.repositories;

import com.careem.voice.notes.service.models.VoiceNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceNoteRepository extends CrudRepository<VoiceNote,Long> {
}
