package ru.practicum.ewm.comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    //todo: Комментирование:
    // Может ли пользователь, оставивший комментарий, отредактировать его позже?
    // Возможность оставлять комментарии к событиям и модерировать их.

    //Возможность получения всех имеющихся комментариев события

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/event/{eventId}")
    public List<CommentDtoOut> getAllCommentsByEventId(
            @Valid @Positive @PathVariable Long eventId) {
        log.info("Получаем GET запрос к эндпойнту /comments/event/{}", eventId);
        return commentService.getCommentsByEventId(eventId);
    }
}
