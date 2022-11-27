package ru.practicum.explore_with_me.statistic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.statistic.dto.EndpointHit;
import ru.practicum.explore_with_me.statistic.dto.ViewStats;
import ru.practicum.explore_with_me.statistic.service.StatService;

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
            @RequestParam(required = false, name = "start",
                    defaultValue = "0001-01-01 00:00:00") String start,
            @RequestParam(required = false, name = "end",
                    defaultValue = "9000-01-01 00:00:00") String end,
            @RequestParam(required = false, name = "uris") String uris,
            @RequestParam(required = false, name = "unique",
            defaultValue = "true") Boolean unique) {
        log.info("Получаем GET запрос к эндпойнту /stats");
        return statService.getStatistics(start, end, uris, unique);
    }
}
