package ru.practicum.explore_with_me.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Long participantLimit;
    private String title;
    private String created;
    private Long event;
    private Long requester;
    private String status;
}
