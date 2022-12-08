package ru.practicum.ewm.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.event.model.Status;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventIdAndStatus(Long eventId, Status status);

    List<Comment> findAllByAuthorId(Long userId);

    Boolean existsByAuthorIdAndEventId(Long userId, Long eventId);
}
