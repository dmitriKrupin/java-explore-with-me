package ru.practicum.ewm.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @Autowired
    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationDto> getAllCompilations() {
        log.info("Получаем GET запрос к эндпойнту /compilations");
        return compilationService.getAllCompilations();
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@Validated @Positive @PathVariable Long compId) {
        log.info("Получаем GET запрос к эндпойнту /compilations/{}", compId);
        return compilationService.getCompilationById(compId);
    }
}
