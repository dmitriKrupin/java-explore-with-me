package ru.practicum.ewm.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Autowired
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
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Boolean existRequestFromUserAtEvent = requestRepository
                .existsByRequesterIdAndEventId(userId, eventId);
        Request request = new Request(
                event, user, LocalDateTime.now(), Status.PENDING);
        if (!existRequestFromUserAtEvent && event.getParticipantLimit() > 0) {
            Long confirmedRequest = requestRepository
                    .countEventByStatus(eventId, Status.CONFIRMED);
            long requestLimit = event.getParticipantLimit() - confirmedRequest;
            //Ограничение на количество участников.
            // Значение 0 - означает отсутствие ограничения
            //Количество одобренных заявок на участие в данном событии
            if (requestLimit == 0) {
                // + если у события достигнут лимит запросов на участие необходимо вернуть ошибку
                throw new BadRequestException("Достигнут лимит запросов на событие с id " + eventId);
            }
            if (event.getState().equals(Status.PUBLISHED) &&
                    !event.getInitiator().getId().equals(userId)) {
                if (!event.getRequestModeration()) {
                    // + если для события отключена пре-модерация запросов на участие,
                    // то запрос должен автоматически перейти в состояние подтвержденного
                    request.setStatus(Status.CONFIRMED);
                }
            } else {
                request.setStatus(Status.PENDING);
            }
            requestRepository.save(request);
            return RequestMapper.toParticipationRequestDto(request);
        } else {
            throw new BadRequestException("Ошибка запроса для события с id " + eventId);
        }
    }

    @Override
    public ParticipationRequestDto canceledOwnRequestByUser(Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + requestId + " нет"));
        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }
}
