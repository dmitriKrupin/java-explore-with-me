package ru.practicum.explore_with_me.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.mapper.CompilationMapper;
import ru.practicum.explore_with_me.compilation.model.Compilation;
import ru.practicum.explore_with_me.compilation.repository.CompilationRepository;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
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
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }
}
