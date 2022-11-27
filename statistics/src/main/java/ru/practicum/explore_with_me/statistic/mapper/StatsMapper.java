package ru.practicum.explore_with_me.statistic.mapper;

import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;
import ru.practicum.explore_with_me.statistic.model.Stats;

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
