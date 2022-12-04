package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByRequesterId(Long userId);

    @Query(" select count(r.event.id) from Request r " +
            "where r.event.id = :eventId " +
            " and r.status = :status ")
    Long countEventByStatus(Long eventId, Status status);

    Boolean existsByRequesterIdAndEventId(Long eventId, Long userId);
}
