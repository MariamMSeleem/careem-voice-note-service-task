package com.careem.voice.notes.service.repositories;

import com.careem.voice.notes.service.models.Rider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends CrudRepository<Rider,Long> {
}
