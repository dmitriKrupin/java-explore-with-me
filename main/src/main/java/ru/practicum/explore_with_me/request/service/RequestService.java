package ru.practicum.explore_with_me.request.service;

import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getAllRequestsByUserInAnotherEvents(Long userId);

    ParticipationRequestDto addRequestByUserInAnotherEvent(Long userId, Long eventId);

    ParticipationRequestDto canceledOwnRequestByUser(Long userId, Long requestId);
}
