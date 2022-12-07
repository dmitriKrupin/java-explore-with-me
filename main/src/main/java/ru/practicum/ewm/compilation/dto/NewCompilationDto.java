package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    @NotNull
    @NotBlank
    @NotEmpty
    private String title;
    private List<Long> events;
    @Value("false")
    private Boolean pinned;
}
