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

    //String text, String categories, Boolean paid, String rangeStart,
    //String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size
    /*@Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))" +
            " and i.available = true ")*/
    //List<Item> findItemListBySearch(String text);

    List<Event> findAllByAnnotationLikeAndCategoryIdAndPaid(
            String annotation, Long category_id, Boolean paid);

    //List<Long> users, List<String> states, List<Long> categories,
    //String rangeStart, String rangeEnd, Long from, Long size
    List<Event> findAllByInitiator_IdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
            Collection<Long> initiator_id, Collection<Status> state,
            Collection<Long> category_id, LocalDateTime rangeStart, LocalDateTime rangeEnd);
}
