package ru.practicum.ewm.statistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.statistic.dto.ViewStats;
import ru.practicum.ewm.statistic.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stats, Long> {
    @Query("select new ru.practicum.ewm.statistic.dto.ViewStats(s.app, s.uri, count(distinct s.ip))" +
            " from Stats as s" +
            " where s.timestamp between ?1 and ?2" +
            " and s.uri in ?3" +
            " group by s.app, s.uri")
    List<ViewStats> getUniqueStatsByUris(
            LocalDateTime startTime, LocalDateTime endTime, List<String> uris);

    @Query("select new ru.practicum.ewm.statistic.dto.ViewStats(s.app, s.uri, count(s.ip))" +
            " from Stats as s " +
            " where s.timestamp between ?1 and ?2" +
            " and s.uri in ?3" +
            " group by s.app, s.uri")
    List<ViewStats> getStatsByUris(
            LocalDateTime startTime, LocalDateTime endTime, List<String> uris);
}
