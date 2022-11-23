package ru.practicum.explore_with_me.statistic.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;
import ru.practicum.explore_with_me.statistic.mapper.StatsMapper;
import ru.practicum.explore_with_me.statistic.model.Stats;
import ru.practicum.explore_with_me.statistic.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public void saveRequestToEndpoint(EndpointHit endpointHit) {
        //Сохранение информации о том, что к эндпоинту был запрос
        Long hits = 0L;
        hits++;
        Stats stats = StatsMapper.toStats(endpointHit, hits);
        //todo: добавить счетчик просмотров
        statRepository.save(stats);
    }

    @Override
    public List<ViewStats> getStatistics(
            String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Stats> stats = statRepository
                .findAllByTimestampAfterAndTimestampBeforeAndUriIn(
                        startTime, endTime, uris);
        return StatsMapper.toViewStatsList(stats);
    }
}
