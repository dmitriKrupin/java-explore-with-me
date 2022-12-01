package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.ewm.event.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewEventDto {
    //Новое событие
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;
    @NotNull
    @NotEmpty
    @NotBlank
    private String eventDate;
    @NotNull
    private Location location;
    @Value("false")
    private Boolean paid;
    @Value("0")
    private Long participantLimit;
    @Value("true")
    private Boolean requestModeration;
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 120)
    private String title;
}
