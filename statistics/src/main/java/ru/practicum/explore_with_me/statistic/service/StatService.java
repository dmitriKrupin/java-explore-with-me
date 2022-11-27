package ru.practicum.explore_with_me.statistic.service;

import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;

import java.util.List;

public interface StatService {
    void saveRequestToEndpoint(EndpointHit endpointHit);

    List<ViewStats> getStatistics(String start, String end, String uris, Boolean unique);
}
