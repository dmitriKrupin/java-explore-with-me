package ru.practicum.explore_with_me.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.event.repository.EventRepository;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.mapper.RequestMapper;
import ru.practicum.explore_with_me.request.model.Request;
import ru.practicum.explore_with_me.request.repository.RequestRepository;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    public RequestServiceImpl(
            UserRepository userRepository,
            EventRepository eventRepository,
            RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.requestRepository = requestRepository;

    }

    @Override
    public List<ParticipationRequestDto> getAllRequestsByUserInAnotherEvents(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        return RequestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto addRequestByUserInAnotherEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        Request request = new Request(event, user, LocalDateTime.now(), event.getState());
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto canceledOwnRequestByUser(Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Такой заявки c id " + requestId + " нет"));
        request.setStatus(Status.FORBIDDEN);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }
}
