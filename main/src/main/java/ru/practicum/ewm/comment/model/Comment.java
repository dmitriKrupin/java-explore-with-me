package ru.practicum.ewm.comment.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text")
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "status")
    private Status status;
    @Column(name = "comment_moderation")
    private Boolean commentModeration;
    @Column(name = "published")
    private LocalDateTime published;
    @Column(name = "edited")
    private LocalDateTime edited;

    public Comment(String text, Event event, User author, LocalDateTime created,
                   Boolean commentModeration, LocalDateTime published,
                   LocalDateTime edited) {
        this.text = text;
        this.event = event;
        this.author = author;
        this.created = created;
        this.commentModeration = commentModeration;
        this.published = published;
        this.edited = edited;
    }
}
