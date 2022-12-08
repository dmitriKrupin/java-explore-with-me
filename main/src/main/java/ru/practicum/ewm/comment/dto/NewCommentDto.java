package ru.practicum.ewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewCommentDto {
    @NotEmpty
    @Length(min = 5, max = 7000)
    private String text;
    @NotNull
    private String created;
}
