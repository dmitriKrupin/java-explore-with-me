package ru.practicum.explore_with_me.compilation.mapper;

import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.model.Compilation;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.mapper.EventMapper;
import ru.practicum.explore_with_me.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {
    public static Compilation toCompilation(
            NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned(),
                events);
    }

    public static CompilationDto toCompilationDto(
            Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(
                events,
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation entry : compilations) {
            compilationDtoList.add(CompilationMapper
                    .toCompilationDto(entry, EventMapper.toEventShortDtoList(entry.getEvents())));
        }
        return compilationDtoList;
    }
}
