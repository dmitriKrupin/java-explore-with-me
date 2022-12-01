package ru.practicum.ewm.statistic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.statistic.dto.EndpointHit;
import ru.practicum.ewm.statistic.dto.ViewStats;
import ru.practicum.ewm.statistic.service.StatService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatsController {
    private final StatService statService;

    @Autowired
    public StatsController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping("/hit")
    public void saveRequestToEndpoint(@RequestBody EndpointHit endpointHit) {
        log.info("Получаем POST запрос к эндпойнту /hit");
        statService.saveRequestToEndpoint(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(
            @RequestParam(required = false,
                    defaultValue = "0001-01-01 00:00:00") String start,
            @RequestParam(required = false,
                    defaultValue = "9000-01-01 00:00:00") String end,
            @RequestParam(required = false) String uris,
            @RequestParam(required = false,
                    defaultValue = "false") Boolean unique) {
        log.info("Получаем GET запрос к эндпойнту /stats");
        return statService.getStatistics(start, end, uris, unique);
    }
}
