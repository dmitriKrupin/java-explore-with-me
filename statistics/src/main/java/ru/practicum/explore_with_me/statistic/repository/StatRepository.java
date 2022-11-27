package ru.practicum.explore_with_me.statistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.statistic.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stats, Long> {
    List<Stats> findAllByTimestampAfterAndTimestampBeforeAndUriIn(
            LocalDateTime startTime, LocalDateTime endTime, List<String> uris);
}
