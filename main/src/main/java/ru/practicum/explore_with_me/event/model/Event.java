package ru.practicum.explore_with_me.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events", schema = "public")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Boolean paid;
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private Long views;
    private Long confirmedRequests;
    private String description;
    private Long participantLimit;
    private Status state;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private Boolean requestModeration;

    public Event(String title, String annotation, Category category, Boolean paid, LocalDateTime eventDate,
                 User initiator, Long views, Long confirmedRequests, String description, Long participantLimit,
                 Status state, LocalDateTime createdOn, LocalDateTime publishedOn, Location location,
                 Boolean requestModeration) {
        this.title = title;
        this.annotation = annotation;
        this.category = category;
        this.paid = paid;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.views = views;
        this.confirmedRequests = confirmedRequests;
        this.description = description;
        this.participantLimit = participantLimit;
        this.state = state;
        this.createdOn = createdOn;
        this.publishedOn = publishedOn;
        this.location = location;
        this.requestModeration = requestModeration;
    }
}
