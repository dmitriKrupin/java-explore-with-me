package ru.practicum.ewm.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.AdminUpdateEventRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {
    private final EventService eventService;

    @Autowired
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventFullDto> findAllEventsByParams(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false,
                    defaultValue = "PUBLISHED") List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false,
                    defaultValue = "0001-01-01 00:00:00") String rangeStart,
            @RequestParam(required = false,
                    defaultValue = "9000-01-01 00:00:00") String rangeEnd,
            @RequestParam(required = false, defaultValue = "0") Long from,
            @RequestParam(required = false, defaultValue = "10") Long size) {
        log.info("Получаем GET запрос к эндпойнту /admin/events");
        return eventService.findAllEventsByParams(
                users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEventById(
            @Valid @Positive @PathVariable Long eventId,
            @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Получаем PUT запрос к эндпойнту /admin/events/{}", eventId);
        return eventService.updateEventById(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishedEvent(
            @Valid @Positive @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/events/{}/publish", eventId);
        return eventService.publishedEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectedEvent(
            @Valid @Positive @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/events/{}/reject", eventId);
        return eventService.rejectedEvent(eventId);
    }

}
