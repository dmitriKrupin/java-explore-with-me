package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.service.EventService;
import ru.practicum.explore_with_me.request.dto.AdminUpdateEventRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    private List<EventFullDto> findAllEventsByParams(
            @RequestParam List<Long> users,
            @RequestParam List<String> states,
            @RequestParam List<Long> categories,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam(required = false, name = "from", defaultValue = "0") Long from,
            @RequestParam(required = false, name = "size", defaultValue = "10") Long size) {
        log.info("Получаем GET запрос к эндпойнту /admin/events");
        return eventService.findAllEventsByParams(
                users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    private EventFullDto updateEventById(
            @PathVariable Long eventId,
            @RequestBody AdminUpdateEventRequest adminUpdateEventRequest) {
        log.info("Получаем PUT запрос к эндпойнту /admin/events/{}", eventId);
        return eventService.updateEventById(eventId, adminUpdateEventRequest);
    }

    @PatchMapping("/{eventId}/publish")
    private EventFullDto publishedEvent(
            @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/events/{}/publish", eventId);
        return eventService.publishedEvent(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    private EventFullDto rejectedEvent(
            @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/events/{}/reject", eventId);
        return eventService.rejectedEvent(eventId);
    }

}
