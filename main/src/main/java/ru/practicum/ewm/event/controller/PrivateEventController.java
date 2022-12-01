package ru.practicum.ewm.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.UpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @Autowired
    public PrivateEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventShortDto> getAllEventsByUser(
            @Validated @Positive @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events", userId);
        return eventService.getAllEventsByUser(userId);
    }

    @PatchMapping
    public EventFullDto updateEventByUser(
            @Validated @Positive @PathVariable Long userId,
            @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events", userId);
        return eventService.updateEventByUser(userId, updateEventRequest);
    }

    @PostMapping
    public EventFullDto addEvent(
            @Validated @Positive @PathVariable Long userId,
            @Valid @RequestBody NewEventDto newEventDto)
            throws IOException, InterruptedException, URISyntaxException {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events", userId);
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByUser(
            @Validated @Positive @PathVariable Long userId,
            @Validated @Positive @PathVariable Long eventId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.getEventByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto cancelEventByUser(
            @Validated @Positive @PathVariable Long userId,
            @Validated @Positive @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.cancelEventByUser(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getAllRequestsByUser(
            @Validated @Positive @PathVariable Long userId,
            @Validated @Positive @PathVariable Long eventId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests", userId, eventId);
        return eventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmedRequestByUser(
            @Validated @Positive @PathVariable Long userId,
            @Validated @Positive @PathVariable Long eventId,
            @Validated @Positive @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.confirmedRequestByUser(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectedRequestByUser(
            @Validated @Positive @PathVariable Long userId,
            @Validated @Positive @PathVariable Long eventId,
            @Validated @Positive @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.rejectedRequestByUser(userId, eventId, reqId);
    }
}
