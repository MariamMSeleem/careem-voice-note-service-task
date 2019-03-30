package com.careem.voice.notes.service.repositories;

import com.careem.voice.notes.service.models.Journey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends CrudRepository<Journey,Long> {
    Journey findByTrackingId(String trackingId);
}
