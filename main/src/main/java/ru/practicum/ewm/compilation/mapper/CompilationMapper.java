package ru.practicum.ewm.compilation.mapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.common.AddAndGetViewsForEvents;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
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

    public static List<CompilationDto> toCompilationDtoList(
            List<Compilation> compilations, AddAndGetViewsForEvents addAndGetViewsForEvents) {
        List<CompilationDto> list = new ArrayList<>();
        for (Compilation entry : compilations) {
            Map<Event, Long> viewsOfEvents = addAndGetViewsForEvents
                    .getMapViewsOfEvents(entry.getEvents());
            CompilationDto compilationDto = CompilationMapper
                    .toCompilationDto(entry, EventMapper.toEventShortDtoList(viewsOfEvents));
            list.add(compilationDto);
        }
        return list;
    }
}
