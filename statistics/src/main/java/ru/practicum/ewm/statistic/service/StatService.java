package ru.practicum.ewm.statistic.service;

import ru.practicum.ewm.statistic.dto.EndpointHit;
import ru.practicum.ewm.statistic.dto.ViewStats;

import java.util.List;

public interface StatService {
    void saveRequestToEndpoint(EndpointHit endpointHit);

    List<ViewStats> getStatistics(String start, String end, String uris, Boolean unique);
}
