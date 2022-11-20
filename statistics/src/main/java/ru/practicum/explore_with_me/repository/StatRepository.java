package ru.practicum.explore_with_me.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.model.Stats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatRepository extends JpaRepository<Stats, Long> {
    List<Stats> findAllByTimestampAfterAndTimestampBeforeAndUriIn(
            LocalDateTime startTime, LocalDateTime endTime, Collection<String> uris);
}
