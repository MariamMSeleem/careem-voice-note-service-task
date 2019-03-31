package com.careem.voice.notes.service.models.repositories;

import com.careem.voice.notes.service.models.entities.Rider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends CrudRepository<Rider,Long> {
}
