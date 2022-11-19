package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.service.EventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    private List<EventShortDto> getAllEvents(
            @RequestParam(required = false, name = "text") String text,
            @RequestParam(required = false, name = "categories") String categories,
            @RequestParam(required = false, name = "paid") Boolean paid,
            @RequestParam(required = false, name = "rangeStart") String rangeStart,
            @RequestParam(required = false, name = "rangeEnd") String rangeEnd,
            @RequestParam(required = false, name = "onlyAvailable") Boolean onlyAvailable,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, name = "from", defaultValue = "0") Long from,
            @RequestParam(required = false, name = "size", defaultValue = "10") Long size) {
        log.info("Получаем GET запрос к эндпойнту /events");
        return eventService.getAllEvents(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    private EventFullDto getEventById(@PathVariable Long id) {
        log.info("Получаем GET запрос к эндпойнту /events/{}", id);
        return eventService.getEventById(id);
    }
}
