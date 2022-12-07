package ru.practicum.ewm.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Slf4j
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {
    private final CommentService commentService;

    @Autowired
    public AdminCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PatchMapping("/{commentId}/publish")
    public CommentDtoOut publishedComment(
            @Valid @Positive @PathVariable Long commentId) {
        log.info("Получаем PATCH запрос к эндпойнту /comment/{}/publish", commentId);
        return commentService.publishedComment(commentId);
    }

    @PatchMapping("/{commentId}/reject")
    public CommentDtoOut rejectedComment(
            @Valid @Positive @PathVariable Long commentId) {
        log.info("Получаем PATCH запрос к эндпойнту /comment/{}/reject", commentId);
        return commentService.rejectedComment(commentId);
    }

}
