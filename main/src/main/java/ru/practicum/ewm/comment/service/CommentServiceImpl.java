package ru.practicum.ewm.comment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentDtoOut;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDtoOut> getCommentsByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события с id " + eventId + " нет!"));
        List<Comment> comments = commentRepository.findAllByEventId(eventId);
        return CommentMapper.toCommentDtoOutList(comments);
    }

    @Override
    public CommentDtoOut publishedComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария c id " + commentId + " нет!"));
        if (comment.getCommentModeration()) {
            comment.setPublished(LocalDateTime.now());
            comment.setStatus(Status.PUBLISHED);
            commentRepository.save(comment);
            return CommentMapper.toCommentDtoOut(
                    comment, comment.getEvent(), comment.getAuthor());
        } else {
            throw new BadRequestException("Комментарий с id " + commentId + " уже опубликован.");
        }
    }

    @Override
    public CommentDtoOut rejectedComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария c id " + commentId + " нет!"));
        if (comment.getCommentModeration()) {
            comment.setPublished(LocalDateTime.now());
            comment.setStatus(Status.REJECTED);
            commentRepository.save(comment);
            return CommentMapper.toCommentDtoOut(
                    comment, comment.getEvent(), comment.getAuthor());
        } else {
            throw new BadRequestException("Комментарий с id " + commentId + " уже опубликован.");
        }
    }

    @Override
    public CommentDtoOut addComment(
            NewCommentDto newCommentDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события с id " + eventId + " нет!"));
        Comment comment = CommentMapper.toComment(
                newCommentDto, event, user);
        comment.setStatus(Status.PENDING);
        commentRepository.save(comment);
        return CommentMapper.toCommentDtoOut(comment, event, user);
    }

    @Override
    public CommentDtoOut updateComment(
            UpdateCommentDto updateCommentDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет!"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события с id " + eventId + " нет!"));
        Long commentId = updateCommentDto.getCommentId();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария c id " + commentId + " нет!"));
        if (Objects.equals(comment.getAuthor().getId(), userId)) {
            comment.setText(updateCommentDto.getText());
            comment.setEdited(LocalDateTime.now());
            commentRepository.save(comment);
            return CommentMapper.toCommentDtoOut(comment, event, user);
        } else {
            throw new BadRequestException("Пользователь с id " + userId + " не может вносить изменения в этот комментарий.");
        }
    }

    @Override
    public void deleteCommentById(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет!"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария c id " + commentId + " нет!"));
        if (Objects.equals(comment.getAuthor().getId(), userId)) {
            commentRepository.delete(comment);
        } else {
            throw new BadRequestException("Пользователь с id " + userId + " не может удалить этот комментарий.");
        }
    }

    @Override
    public List<CommentDtoOut> getAllCommentsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет!"));
        List<Comment> comments = commentRepository.findAllByAuthorId(userId);
        return CommentMapper.toCommentDtoOutList(comments);
    }
}
