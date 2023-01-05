package com.deeperconnections.domain;

import static io.micronaut.data.annotation.GeneratedValue.Type.AUTO;

import io.micronaut.data.annotation.GeneratedValue;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class store info about an event.
 * An event correspond to an experience to offer or sell.
 */
@Entity
@Getter
@Table(name = "events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(AUTO)
    int id;

    @NotNull
    String name;

    String description;

    @NotBlank
    String local;

    Float cost;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    Boolean active = Boolean.TRUE;
}
