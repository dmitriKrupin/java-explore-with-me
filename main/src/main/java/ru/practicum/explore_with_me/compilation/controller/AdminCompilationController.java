package ru.practicum.explore_with_me.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.service.CompilationService;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    @Autowired
    private CompilationService compilationService;

    @PostMapping
    private CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/compilations");
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    private void deleteCompilation(@PathVariable Long compId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}", compId);
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    private void deleteEventFromCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}/events/{}", compId, eventId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    private void addEventToCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/compilations/{}/events/{}", compId, eventId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    private void unpinCompilationOnMainSize(@PathVariable Long compId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}/pin", compId);
        compilationService.unpinCompilationOnMainSize(compId);
    }

    @PatchMapping("/{compId}/pin")
    private void pinCompilationOnMainSize(@PathVariable Long compId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/compilations/{}/pin", compId);
        compilationService.pinCompilationOnMainSize(compId);
    }
}
