package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.service.EventService;

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
            @RequestParam Long from,
            @RequestParam Long size) {
        log.info("Получаем GET запрос к эндпойнту /admin/events");
        return eventService.findAllEventsByParams(
                users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
