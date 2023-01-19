package ru.practicum.ewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.user.dto.UserShortDto;

@Data
@AllArgsConstructor
public class CommentDtoOut {
    private String title;
    private Long id;
    private UserShortDto author;
    private String text;
    private String status;
    private String created;
    private String edited;
    private String published;
}
