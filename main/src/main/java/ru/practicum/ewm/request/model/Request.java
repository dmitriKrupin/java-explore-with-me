package ru.practicum.ewm.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
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
