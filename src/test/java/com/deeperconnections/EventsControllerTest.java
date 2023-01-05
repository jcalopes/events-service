package com.deeperconnections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@MicronautTest
class EventsControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void Should_GetCreatedEvent_When_CreatedSingleEvent() {
        // Created event by mutation type
        HttpResponse<Map> mutationRsp = createEvent("New year's eve", "Event created by mutation", "Lisboa", 13.6f);
        assertEquals(HttpStatus.OK, mutationRsp.status());

        // Get for all events by query type
        HttpResponse<Map> rsp = getAllEvents();
        assertEquals(HttpStatus.OK, rsp.status());
        assertNotNull(rsp.body());

        Map eventsInfo = (Map) rsp.getBody(Map.class).get().get("data");
        ArrayList events = (ArrayList) eventsInfo.get("events");
        assertEquals(events.size(), 1);

        Map event = (Map) events.get(0);
        assertEquals("New year's eve", event.get("name"));
        assertEquals("Event created by mutation", event.get("description"));
        assertEquals(13.6, event.get("cost"));
    }

    private HttpResponse<Map> createEvent(String name, String description, String local, Float cost) {
        String mutation = "{\"query\": \"mutation { createEvent(name: \\\"" + name + "\\\", description: \\\""
                + description + "\\\", local: \\\"" + local + "\\\", cost: " + cost + ") { id } }\" }";
        HttpRequest<String> mutationRequest = HttpRequest.POST("/graphql", mutation);
        return client.toBlocking().exchange(mutationRequest, Argument.of(Map.class));
    }

    private HttpResponse<Map> getAllEvents() {
        String query = "{ \"query\": \"{ events { name, description, cost } }\" }";
        HttpRequest<String> queryRequest = HttpRequest.POST("/graphql", query);
        return client.toBlocking().exchange(queryRequest, Argument.of(Map.class));
    }
}
