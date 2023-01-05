package com.deeperconnections.controller;

import com.deeperconnections.domain.Event;
import com.deeperconnections.repository.EventRepository;
import graphql.schema.DataFetcher;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * This class stores all data fetchers which will perform CRUD operations against the repository.
 */
@Singleton
@RequiredArgsConstructor
public class DataFetchers {
    private final EventRepository dbRepository;

    /**
     * Get all events associated to the local passed as argument.
     * @return All events.
     */
    public DataFetcher<Iterable<Event>> getEventsByLocal() {
        return dataFetchingEnvironment -> {
            String targetLocal = dataFetchingEnvironment.getArgument("local");
            return dbRepository.findByLocal(targetLocal);
        };
    }

    /**
     * Get all events stored on the database.
     * @return All events.
     */
    public DataFetcher<Iterable<Event>> getAllEvents() {
        return dataFetchingEnvironment -> dbRepository.findAll();
    }

    /**
     * Create a new event from a performed mutation.
     * @return The event added to the database.
     */
    public DataFetcher<Event> createEvent() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            String description = dataFetchingEnvironment.getArgument("description");
            String local = dataFetchingEnvironment.getArgument("local");
            Float cost;
            try {
                Double value = dataFetchingEnvironment.getArgument("cost");
                cost = value.floatValue();
            } catch (ClassCastException ex) {
                cost = dataFetchingEnvironment.getArgument("cost");
            }

            Event event = Event.builder()
                    .name(name)
                    .description(description)
                    .local(local)
                    .cost(cost)
                    .active(Boolean.TRUE)
                    .build();

            return dbRepository.save(event);
        };
    }
}
