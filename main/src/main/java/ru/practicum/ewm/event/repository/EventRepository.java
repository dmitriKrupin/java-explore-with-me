package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiator(User user);

    List<Event> findAllByIdIn(List<Long> events);

    @Query(" select e from Event e " +
            "where upper(e.annotation) like upper(concat('%', :annotation, '%')) " +
            " or e.category.id in :categories " + //WHERE u.name IN :names
            " or e.paid = :paid ")
    List<Event> findAllEventsByFilters(
            String annotation, @Param("categories") List<Long> categories, Boolean paid);

    List<Event> findAllByAnnotationLikeOrCategoryIdInAndPaidOrderByEventDateAsc(
            String annotation, List<Long> category, Boolean paid);

    List<Event> findAllByAnnotationLikeOrCategoryIdInAndPaidOrderByViewsAsc(
            String annotation, List<Long> category, Boolean paid);

    List<Event> findAllByInitiator_IdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
            Collection<Long> users, Collection<Status> states,
            Collection<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd);
}
