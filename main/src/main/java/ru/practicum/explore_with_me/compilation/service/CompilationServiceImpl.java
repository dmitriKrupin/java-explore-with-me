package ru.practicum.explore_with_me.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.mapper.CompilationMapper;
import ru.practicum.explore_with_me.compilation.model.Compilation;
import ru.practicum.explore_with_me.compilation.repository.CompilationRepository;
import ru.practicum.explore_with_me.event.mapper.EventMapper;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.repository.EventRepository;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getAllCompilations() {
        List<Compilation> compilations = compilationRepository.findAll();
        return CompilationMapper.toCompilationDtoList(compilations);
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        return CompilationMapper.toCompilationDto(
                compilation, EventMapper.toEventShortDtoList(compilation.getEvents()));
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, events);
        compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(
                compilation, EventMapper.toEventShortDtoList(compilation.getEvents()));
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
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        //todo: Удалить событие из подборки
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        //todo: Добавить событие в подборку
    }

    @Override
    public void unpinCompilationOnMainSize(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilationOnMainSize(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + compId + " нет"));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}