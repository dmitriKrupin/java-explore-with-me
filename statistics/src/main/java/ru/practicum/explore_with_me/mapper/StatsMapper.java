package ru.practicum.explore_with_me.mapper;

import ru.practicum.explore_with_me.dto.EndpointHit;
import ru.practicum.explore_with_me.model.Stats;

import java.time.LocalDateTime;

public class StatsMapper {
    public static Stats toStats(EndpointHit endpointHit) {
        return new Stats(
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                LocalDateTime.parse(endpointHit.getTimestamp()));
    }
}
