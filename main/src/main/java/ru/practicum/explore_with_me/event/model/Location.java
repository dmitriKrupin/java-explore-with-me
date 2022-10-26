package ru.practicum.explore_with_me.event.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "locations", schema = "public")
public class Location {
    @Id //todo: переделать на уникальность по lat и lon
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lat;
    private Double lon;
}
