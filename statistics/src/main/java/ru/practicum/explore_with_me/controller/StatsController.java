package ru.practicum.explore_with_me.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.dto.EndpointHit;
import ru.practicum.explore_with_me.dto.ViewStats;
import ru.practicum.explore_with_me.service.StatService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatsController {
    @Autowired
    private StatService statService;

    @PostMapping("/hit")
    public void saveRequestToEndpoint(@RequestBody EndpointHit endpointHit) {
        log.info("Получаем POST запрос к эндпойнту /hit");
        statService.saveRequestToEndpoint(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam List<String> uris,
            @RequestParam Boolean unique) {
        log.info("Получаем GET запрос к эндпойнту /stats");
        return statService.getStatistics(start, end, uris, unique);
    }
}
