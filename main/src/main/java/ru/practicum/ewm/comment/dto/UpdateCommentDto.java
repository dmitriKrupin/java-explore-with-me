package ru.practicum.ewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdateCommentDto {
    @NotNull
    private Long commentId;
    @NotEmpty
    private String text;
    @Value("true")
    private Boolean commentModeration;
    @NotNull
    private String edited;
    @NotNull
    private Long eventId;
}
