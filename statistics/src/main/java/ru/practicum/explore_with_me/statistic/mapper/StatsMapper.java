package ru.practicum.explore_with_me.statistic.mapper;

import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;
import ru.practicum.explore_with_me.statistic.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsMapper {
    public static Stats toStats(EndpointHit endpointHit) {
        return new Stats(
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                LocalDateTime.parse(endpointHit.getTimestamp(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static ViewStats toViewStats(Stats stat) {
        return new ViewStats(
                stat.getApp(), stat.getUri(), stat.getHits());
    }

    public static List<ViewStats> toViewStatsList(List<Stats> stats) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (Stats entry : stats) {
            viewStatsList.add(toViewStats(entry));
        }
        return viewStatsList;
    }
}
