package ru.practicum.explore_with_me.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.repository.EventRepository;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.model.Request;
import ru.practicum.explore_with_me.request.repository.RequestRepository;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

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
        //todo: Получение информации о заявках текущего пользователя на участие в чужих событиях
        return null;
    }

    @Override
    public ParticipationRequestDto addRequestByUserInAnotherEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        //todo: Добавление запроса от текущего пользователя на участие в событии
        return null;
    }

    @Override
    public ParticipationRequestDto canceledOwnRequestByUser(Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Такой заявки c id " + requestId + " нет"));
        //todo: Отмена своего запроса на участие в событии
        return null;
    }
}
