package ru.practicum.explore_with_me.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<EventShortDto> getAllEvents() {
        log.info("Получаем GET запрос к эндпойнту /events");
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id) {
        log.info("Получаем GET запрос к эндпойнту /events/{}", id);
        return eventService.getEventById(id);
    }
}
