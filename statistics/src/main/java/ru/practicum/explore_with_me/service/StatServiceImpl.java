package ru.practicum.explore_with_me.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.dto.EndpointHit;
import ru.practicum.explore_with_me.dto.ViewStats;
import ru.practicum.explore_with_me.mapper.StatsMapper;
import ru.practicum.explore_with_me.model.Stats;
import ru.practicum.explore_with_me.repository.StatRepository;

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
    public List<ViewStats> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        //todo: Получение статистики по посещениям.
        // Обратите внимание: значение даты и времени нужно закодировать
        // (например используя java.net.URLEncoder.encode)
        return null;
    }
}
