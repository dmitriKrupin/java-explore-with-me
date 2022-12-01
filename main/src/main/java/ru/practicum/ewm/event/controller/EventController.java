package ru.practicum.ewm.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventShortDto> getAllEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false, defaultValue = "0001-01-01 00:00:00")
            String rangeStart,
            @RequestParam(required = false, defaultValue = "9000-01-01 00:00:00")
            String rangeEnd,
            @RequestParam(required = false, defaultValue = "false")
            Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "0") Long from,
            @RequestParam(required = false, defaultValue = "10") Long size,
            HttpServletRequest request)
            throws IOException, InterruptedException, URISyntaxException {
        log.info("Получаем GET запрос к эндпойнту /events");
        log.info("Клиент с ip: {}", request.getRemoteAddr());
        log.info("Путь запроса: {}", request.getRequestURI());
        return eventService.getAllEvents(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(
            @Validated @Positive @PathVariable Long id, HttpServletRequest request)
            throws IOException, InterruptedException, URISyntaxException {
        log.info("Получаем GET запрос к эндпойнту /events/{}", id);
        log.info("Клиент с ip: {}", request.getRemoteAddr());
        log.info("Путь запроса: {}", request.getRequestURI());
        return eventService.getEventById(id, request);
    }
}
