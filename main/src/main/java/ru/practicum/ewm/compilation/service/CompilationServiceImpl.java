package ru.practicum.ewm.compilation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.AddAndGetViewsForEvents;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Long from, Long size) {
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned);
        return CompilationMapper.toCompilationDtoList(compilations);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + compId + " нет"));
        Map<Event, Long> viewsOfEvents = AddAndGetViewsForEvents
                .getMapViewsOfEvents(compilation.getEvents());
        return CompilationMapper.toCompilationDto(
                compilation, EventMapper.toEventShortDtoList(viewsOfEvents));
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllByIdInOrTitleLike(
                newCompilationDto.getEvents(), newCompilationDto.getTitle());
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, events);
        compilationRepository.save(compilation);
        Map<Event, Long> viewsOfEvents = AddAndGetViewsForEvents
                .getMapViewsOfEvents(compilation.getEvents());
        return CompilationMapper.toCompilationDto(
                compilation, EventMapper.toEventShortDtoList(viewsOfEvents));
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        compilationRepository.delete(compilation);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Такой подборки c id " + compId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        List<Event> events = compilation.getEvents();
        events.removeIf(e -> eventId.equals(e.getId()));
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Такой подборки c id " + compId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilationOnMainSize(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + compId + " нет"));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilationOnMainSize(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + compId + " нет"));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
