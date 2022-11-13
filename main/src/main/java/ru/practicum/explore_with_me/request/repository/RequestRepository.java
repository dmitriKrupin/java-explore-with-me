package ru.practicum.explore_with_me.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByRequesterId(Long userId);
}
