package com.deeperconnections.repository;

import com.deeperconnections.domain.Event;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.inject.Singleton;

@Singleton
@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    Iterable<Event> findByLocal(String local);
}
