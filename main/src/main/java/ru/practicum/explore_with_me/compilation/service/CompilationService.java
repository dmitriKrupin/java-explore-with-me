package ru.practicum.explore_with_me.compilation.service;

import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getAllCompilations();

    CompilationDto getCompilationById(Long compId);

    CompilationDto addCompilation(NewCompilationDto newCompilationDto);
}
