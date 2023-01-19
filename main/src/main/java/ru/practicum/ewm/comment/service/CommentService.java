package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDtoOut> getCommentsByEventId(Long eventId);

    CommentDtoOut publishedComment(Long commentId);

    CommentDtoOut rejectedComment(Long eventId);

    CommentDtoOut addComment(NewCommentDto newCommentDto, Long userId, Long eventId);

    CommentDtoOut updateComment(
            UpdateCommentDto updateCommentDto, Long userId, Long eventId);

    void deleteCommentById(Long userId, Long commentId);

    List<CommentDtoOut> getAllCommentsByUser(Long userId);
}
