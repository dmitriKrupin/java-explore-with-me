package ru.practicum.explore_with_me.compilation.mapper;

import ru.practicum.explore_with_me.compilation.dto.CompilationDto;
import ru.practicum.explore_with_me.compilation.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilation.model.Compilation;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return new Compilation(
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned(),
                newCompilationDto.getEvents());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getEvents(),
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle()
        );
    }

    public static List<CompilationDto> toCompilationDtoList(List<Compilation> compilations) {
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation entry : compilations) {
            compilationDtoList.add(CompilationMapper.toCompilationDto(entry));
        }
        return compilationDtoList;
    }
}
