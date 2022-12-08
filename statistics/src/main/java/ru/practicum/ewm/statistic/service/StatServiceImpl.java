package ru.practicum.ewm.statistic.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.statistic.dto.EndpointHit;
import ru.practicum.ewm.statistic.dto.ViewStats;
import ru.practicum.ewm.statistic.mapper.StatsMapper;
import ru.practicum.ewm.statistic.model.Stats;
import ru.practicum.ewm.statistic.repository.StatRepository;

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
        Stats stats = StatsMapper.toStats(endpointHit);
        statRepository.save(stats);
    }

    @Override
    public List<ViewStats> getStatistics(String start, String end, String uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<String> urisClear = List.of(
                uris.replace(" ", "").split(","));
        List<ViewStats> viewStats;
        if (unique) {
            viewStats = statRepository.getUniqueStatsByUris(
                    startTime, endTime, urisClear);
        } else {
            viewStats = statRepository.getStatsByUris(
                    startTime, endTime, urisClear);
        }
        return viewStats;
    }
}
