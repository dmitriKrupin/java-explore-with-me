package ru.practicum.explore_with_me.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiator(User user);

    List<Event> findAllByIdIn(List<Long> events);

    List<Event> findAllByAnnotationLikeAndCategoryIdAndPaid(
            String annotation, Long category, Boolean paid);

    List<Event> findAllByInitiator_IdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
            Collection<Long> users, Collection<Status> states,
            Collection<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd);
}
