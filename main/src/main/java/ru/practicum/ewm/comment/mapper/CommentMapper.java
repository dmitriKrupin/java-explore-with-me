package ru.practicum.ewm.comment.mapper;

import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment toComment(
            NewCommentDto newCommentDto, Event event, User user) {
        return new Comment(
                newCommentDto.getText(),
                event,
                user,
                LocalDateTime.parse(newCommentDto.getCreated(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                null,
                null);
    }

    public static CommentDtoOut toCommentDtoOut(
            Comment comment, Event event, User user) {
        return new CommentDtoOut(
                event.getTitle(),
                comment.getId(),
                UserMapper.toUserShortDto(user),
                comment.getText(),
                comment.getStatus().toString(),
                comment.getCreated().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                null,
                null);
    }

    public static CommentDtoOut toUpdatedCommentDtoOut(
            Comment comment, Event event, User user) {
        return new CommentDtoOut(
                event.getTitle(),
                comment.getId(),
                UserMapper.toUserShortDto(user),
                comment.getText(),
                comment.getStatus().toString(),
                comment.getCreated().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getEdited().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                null);
    }

    public static CommentDtoOut toPublishedCommentDtoOut(
            Comment comment, Event event, User user) {
        return new CommentDtoOut(
                event.getTitle(),
                comment.getId(),
                UserMapper.toUserShortDto(user),
                comment.getText(),
                comment.getStatus().toString(),
                comment.getCreated().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getEdited().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")),
                comment.getPublished().format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static List<CommentDtoOut> toCommentDtoOutList(List<Comment> comments) {
        List<CommentDtoOut> commentDtoOutList = new ArrayList<>();
        for (Comment entry : comments) {
            commentDtoOutList.add(CommentMapper.toCommentDtoOut(
                    entry, entry.getEvent(), entry.getAuthor()));
        }
        return commentDtoOutList;
    }

    public static List<CommentDtoOut> toPublishedCommentDtoOutList(List<Comment> comments) {
        List<CommentDtoOut> commentDtoOutList = new ArrayList<>();
        for (Comment entry : comments) {
            commentDtoOutList.add(CommentMapper.toPublishedCommentDtoOut(
                    entry, entry.getEvent(), entry.getAuthor()));
        }
        return commentDtoOutList;
    }
}
