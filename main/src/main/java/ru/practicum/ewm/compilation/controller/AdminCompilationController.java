package ru.practicum.ewm.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;

    @Autowired
    public AdminCompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping
    public CompilationDto addCompilation(
            @Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/compilations");
        return compilationService.addCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(
            @Valid @Positive @PathVariable Long compId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}", compId);
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(
            @Valid @Positive @PathVariable Long compId,
            @Valid @Positive @PathVariable Long eventId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}/events/{}", compId, eventId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(
            @Valid @Positive @PathVariable Long compId,
            @Valid @Positive @PathVariable Long eventId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/compilations/{}/events/{}", compId, eventId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilationOnMainSize(
            @Valid @Positive @PathVariable Long compId) {
        log.info("Получаем DELETE запрос к эндпойнту /admin/compilations/{}/pin", compId);
        compilationService.unpinCompilationOnMainSize(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilationOnMainSize(
            @Valid @Positive @PathVariable Long compId) {
        log.info("Получаем PATCH запрос к эндпойнту /admin/compilations/{}/pin", compId);
        compilationService.pinCompilationOnMainSize(compId);
    }
}
