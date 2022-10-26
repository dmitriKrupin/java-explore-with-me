package ru.practicum.explore_with_me.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.category.repository.CategoryRepository;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.mapper.EventMapper;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.event.repository.EventRepository;
import ru.practicum.explore_with_me.request.dto.AdminUpdateEventRequest;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.request.mapper.RequestMapper;
import ru.practicum.explore_with_me.request.model.Request;
import ru.practicum.explore_with_me.request.repository.RequestRepository;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EventShortDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return EventMapper.toEventShortDtoList(events);
    }

    @Override
    public EventFullDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + id + " нет"));
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getAllEventsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        List<Event> eventsByUser = eventRepository.findAllByInitiator(user);
        return EventMapper.toEventShortDtoList(eventsByUser);
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, UpdateEventRequest updateEventRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Long eventId = updateEventRequest.getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            event.setAnnotation(updateEventRequest.getAnnotation());

            Long categoryId = updateEventRequest.getCategory();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Такой категории c id " + categoryId + " нет"));
            event.setCategory(category);

            event.setDescription(updateEventRequest.getDescription());
            event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate()));
            event.setPaid(updateEventRequest.getPaid());
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
            event.setTitle(updateEventRequest.getTitle());

            eventRepository.save(event);
            return EventMapper.toEventFullDto(event);
        } else {
            throw new RuntimeException("Пользователь с id " + userId + " не может вносить изменения в это событие.");
        }
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = EventMapper.toNewEvent(newEventDto, user);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        if (event.getRequestModeration()) {
            event.setState(Status.CANCELED);
            return EventMapper.toEventFullDto(event);
        } else {
            throw new RuntimeException("Событие с id " + eventId + " не может быть отменено, т.к. прошло модерацию.");
        }
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return RequestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto confirmedRequestByUser(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new RuntimeException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(String.valueOf(Status.CONFIRMED));
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectedRequestByUser(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new RuntimeException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(String.valueOf(Status.PENDING));
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public List<EventFullDto> findAllEventsByParams(List<Long> users, List<String> states, List<Long> categories,
                                                    String rangeStart, String rangeEnd, Long from, Long size) {
        //todo: Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия
        return null;
    }

    @Override
    public EventFullDto updateEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        event.setAnnotation(adminUpdateEventRequest.getAnnotation());

        Long categoryId = adminUpdateEventRequest.getCategory();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + categoryId + " нет"));
        event.setCategory(category);

        event.setDescription(adminUpdateEventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(adminUpdateEventRequest.getEventDate()));
        event.setLocation(adminUpdateEventRequest.getLocation());
        event.setPaid(adminUpdateEventRequest.getPaid());
        event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        event.setTitle(adminUpdateEventRequest.getTitle());

        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto publishedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        event.setState(Status.PUBLISHED);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto rejectedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        event.setState(Status.REJECTED);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }

}
