package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getAllRequestsByUserInAnotherEvents(Long userId);

    ParticipationRequestDto addRequestByUserInAnotherEvent(Long userId, Long eventId);

    ParticipationRequestDto canceledOwnRequestByUser(Long userId, Long requestId);
}
