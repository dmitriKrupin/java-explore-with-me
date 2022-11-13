package ru.practicum.explore_with_me.request.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    private LocalDateTime created;
    private Status status;

    public Request(Event event, User requester, LocalDateTime created, Status status) {
        this.event = event;
        this.requester = requester;
        this.created = created;
        this.status = status;
    }
}
