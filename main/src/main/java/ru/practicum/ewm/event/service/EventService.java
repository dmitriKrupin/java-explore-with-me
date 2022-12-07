package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.ViewsAndCountConfirmed;
import ru.practicum.ewm.request.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.UpdateEventRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface EventService {
    List<EventShortDto> getAllEvents(
            String text, List<Long> categories, Boolean paid, String rangeStart,
            String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size,
            HttpServletRequest request) throws IOException, InterruptedException, URISyntaxException;

    EventFullDto getEventById(Long id, HttpServletRequest request)
            throws IOException, InterruptedException, URISyntaxException;

    List<EventShortDto> getAllEventsByUser(Long userId);

    EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto) throws IOException, InterruptedException, URISyntaxException;

    Location addLocation(NewEventDto newEventDto);

    EventFullDto getEventByUser(Long userId, Long eventId);

    EventFullDto cancelEventByUser(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId);

    ParticipationRequestDto confirmedRequestByUser(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectedRequestByUser(Long userId, Long eventId, Long reqId);

    List<EventFullDto> findAllEventsByParams(
            List<Long> users, List<String> states, List<Long> categories,
            String rangeStart, String rangeEnd, Long from, Long size);

    EventFullDto updateEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest);

    EventFullDto publishedEvent(Long eventId);

    EventFullDto rejectedEvent(Long eventId);

    Map<Event, ViewsAndCountConfirmed> getViewsAndCountConfirmedOfEvents(
            Map<Event, Long> eventsWithCountConfirmed);
}
