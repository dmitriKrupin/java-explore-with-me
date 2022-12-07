package ru.practicum.ewm.common.service;

import java.util.Map;

public interface AddAndGetViews {
    void addHit(String uri, String ip);

    Map<Long, Long> getViewsOfEventsId(String uris);

    Long getViewByEventId(String uris);
}
