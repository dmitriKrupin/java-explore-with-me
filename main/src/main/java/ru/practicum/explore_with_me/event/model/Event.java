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
    private String title; //Заголовок
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Boolean paid; //Нужно ли оплачивать участие
    @Column(name = "event_date")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private Long views; //Количество просмотрев события
    @Column(name = "confirmed_requests")
    private Long confirmedRequests; //Количество одобренных заявок на участие в данном событии
    private String description;
    @Column(name = "participant_limit")
    private Long participantLimit; //Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Status state; //Список состояний жизненного цикла события
    @Column(name = "created_on")
    private LocalDateTime createdOn; //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "published_on")
    private LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    private Boolean requestModeration; //Нужна ли пре-модерация заявок на участие

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
