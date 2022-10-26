package ru.practicum.explore_with_me.compilation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public CompilationDto addCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        log.info("Получаем POST запрос к эндпойнту /admin/compilations");
        return compilationService.addCompilation(newCompilationDto);
    }
}
