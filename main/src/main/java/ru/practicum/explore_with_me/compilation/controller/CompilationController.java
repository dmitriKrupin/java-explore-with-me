package ru.practicum.explore_with_me.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
public class CompilationController {
    @Autowired
    private CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAllCompilations() {
        log.info("Получаем GET запрос к эндпойнту /compilations");
        return compilationService.getAllCompilations();
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("Получаем GET запрос к эндпойнту /compilations/{}", compId);
        return compilationService.getCompilationById(compId);
    }
}
