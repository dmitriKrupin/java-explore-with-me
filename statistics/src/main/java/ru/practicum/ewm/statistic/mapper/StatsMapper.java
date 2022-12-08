package ru.practicum.ewm.statistic.mapper;

import ru.practicum.ewm.statistic.dto.EndpointHit;
import ru.practicum.ewm.statistic.dto.ViewStats;
import ru.practicum.ewm.statistic.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatsMapper {
    public static Stats toStats(EndpointHit endpointHit) {
        return new Stats(
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                LocalDateTime.parse(endpointHit.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static ViewStats toViewStats(Stats stat, Long hits) {
        return new ViewStats(
                stat.getApp(), stat.getUri(), hits);
    }
}
