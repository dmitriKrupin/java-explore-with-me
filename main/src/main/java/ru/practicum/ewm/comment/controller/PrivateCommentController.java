package ru.practicum.ewm.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/comments")
public class PrivateCommentController {
    private final CommentService commentService;

    @Autowired
    public PrivateCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/event/{eventId}")
    public CommentDtoOut addComment(
            @Valid @Positive @PathVariable Long eventId,
            @Valid @Positive @PathVariable Long userId,
            @RequestBody NewCommentDto newCommentDto) {
        log.info("Получаем POST запрос к эндпойнту /users/{}/comments/event/{}",
                userId, eventId);
        return commentService.addComment(newCommentDto, userId, eventId);
    }

    @PutMapping("/event/{eventId}")
    public CommentDtoOut updateComment(
            @Valid @Positive @PathVariable Long eventId,
            @Valid @Positive @PathVariable Long userId,
            @RequestBody UpdateCommentDto updateCommentDto) {
        log.info("Получаем PUT запрос к эндпойнту /users/{}/comments/event/{}",
                userId, eventId);
        return commentService.updateComment(updateCommentDto, userId, eventId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(
            @Valid @Positive @PathVariable Long commentId,
            @Valid @Positive @PathVariable Long userId) {
        log.info("Получаем DELETE запрос к эндпойнту /users/{}/comments/{}", userId, commentId);
        commentService.deleteCommentById(userId, commentId);
    }

    @GetMapping
    public List<CommentDtoOut> getAllCommentsByUser(
            @Valid @Positive @PathVariable Long userId) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/comments", userId);
        return commentService.getAllCommentsByUser(userId);
    }
}
