package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.dto.UpdateEventRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    private List<EventShortDto> getAllEventsByUser(
            @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events", userId);
        return eventService.getAllEventsByUser(userId);
    }

    @PatchMapping
    private EventFullDto updateEventByUser(
            @PathVariable Long userId,
            @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events", userId);
        return eventService.updateEventByUser(userId, updateEventRequest);
    }

    @PostMapping
    private EventFullDto addEvent(
            @PathVariable Long userId,
            @RequestBody NewEventDto newEventDto) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events", userId);
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    private EventFullDto getEventByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.getEventByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    private EventFullDto cancelEventByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.cancelEventByUser(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    private List<ParticipationRequestDto> getAllRequestsByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests", userId, eventId);
        return eventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    private ParticipationRequestDto confirmedRequestByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.confirmedRequestByUser(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    private ParticipationRequestDto rejectedRequestByUser(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.rejectedRequestByUser(userId, eventId, reqId);
    }
}
