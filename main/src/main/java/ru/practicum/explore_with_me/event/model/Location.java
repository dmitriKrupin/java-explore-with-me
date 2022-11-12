package ru.practicum.explore_with_me.event.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "locations", schema = "public")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float lat;
    private Float lon;
}
