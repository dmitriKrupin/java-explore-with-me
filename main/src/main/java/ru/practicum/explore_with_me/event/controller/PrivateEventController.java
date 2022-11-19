package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.dto.UpdateEventRequest;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    private List<EventShortDto> getAllEventsByUser(
            @Validated @PositiveOrZero @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events", userId);
        return eventService.getAllEventsByUser(userId);
    }

    @PatchMapping
    private EventFullDto updateEventByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events", userId);
        return eventService.updateEventByUser(userId, updateEventRequest);
    }

    @PostMapping
    private EventFullDto addEvent(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @RequestBody NewEventDto newEventDto) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events", userId);
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    private EventFullDto getEventByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @PositiveOrZero @PathVariable Long eventId) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.getEventByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    private EventFullDto cancelEventByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @PositiveOrZero @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /users/{}/events/{}", userId, eventId);
        return eventService.cancelEventByUser(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    private List<ParticipationRequestDto> getAllRequestsByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @PositiveOrZero @PathVariable Long eventId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests", userId, eventId);
        return eventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    private ParticipationRequestDto confirmedRequestByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @PositiveOrZero @PathVariable Long eventId,
            @Validated @PositiveOrZero @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.confirmedRequestByUser(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    private ParticipationRequestDto rejectedRequestByUser(
            @Validated @PositiveOrZero @PathVariable Long userId,
            @Validated @PositiveOrZero @PathVariable Long eventId,
            @Validated @PositiveOrZero @PathVariable Long reqId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/events/{}/requests/{}/confirm", userId, eventId, reqId);
        return eventService.rejectedRequestByUser(userId, eventId, reqId);
    }
}
