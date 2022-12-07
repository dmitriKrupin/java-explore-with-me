package ru.practicum.ewm.common.service;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.ViewsAndCountConfirmed;

import java.util.Map;

public interface AddAndGetViews {
    void addHit(String uri, String ip);

    Map<Event, ViewsAndCountConfirmed> getViewsAndCountConfirmedOfEvents(
            Map<Event, Long> eventsWithCountConfirmed);

    Map<Long, Long> getViewsOfEventsId(String uris);

    Long getViewByEventId(String uris);
}
