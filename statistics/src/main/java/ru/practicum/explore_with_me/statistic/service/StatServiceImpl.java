package ru.practicum.explore_with_me.statistic.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;
import ru.practicum.explore_with_me.statistic.mapper.StatsMapper;
import ru.practicum.explore_with_me.statistic.model.Stats;
import ru.practicum.explore_with_me.statistic.repository.StatRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public void saveRequestToEndpoint(EndpointHit endpointHit) {
        //Сохранение информации о том, что к эндпоинту был запрос
        Stats stats = StatsMapper.toStats(endpointHit);
        statRepository.save(stats);
    }

    @Override
    public List<ViewStats> getStatistics(
            String start, String end, String uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<String> urisClear = List.of(
                uris.replace(" ", "").split(","));

        List<Stats> stats = statRepository
                .findAllByTimestampAfterAndTimestampBeforeAndUriIn(
                        startTime, endTime, urisClear);

        List<ViewStats> viewStatsList = new ArrayList<>();
        Map<Stats, Long> mapOfViewStats = new HashMap<>();
        Stats statsForMap = new Stats();
        for (int i = 0; i < urisClear.size(); i++) {
            Long hit = 0L;
            for (int j = 0; j < stats.size(); j++) {
                if (stats.get(j).getUri().equals(urisClear.get(i))) {
                    hit++;
                    statsForMap = stats.get(j);
                }
            }
            mapOfViewStats.put(statsForMap, hit);
        }
        for (Map.Entry<Stats, Long> entry : mapOfViewStats.entrySet()) {
            viewStatsList.add(StatsMapper.toViewStats(entry.getKey(), entry.getValue()));
        }
        return viewStatsList;
    }
}
