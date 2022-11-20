package ru.practicum.explore_with_me.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.category.model.Category;
import ru.practicum.explore_with_me.category.repository.CategoryRepository;
import ru.practicum.explore_with_me.event.dto.EventFullDto;
import ru.practicum.explore_with_me.event.dto.EventShortDto;
import ru.practicum.explore_with_me.event.dto.NewEventDto;
import ru.practicum.explore_with_me.event.mapper.EventMapper;
import ru.practicum.explore_with_me.event.model.Event;
import ru.practicum.explore_with_me.event.model.Location;
import ru.practicum.explore_with_me.event.model.Status;
import ru.practicum.explore_with_me.event.repository.EventRepository;
import ru.practicum.explore_with_me.event.repository.LocationRepository;
import ru.practicum.explore_with_me.exception.NotFoundException;
import ru.practicum.explore_with_me.request.dto.AdminUpdateEventRequest;
import ru.practicum.explore_with_me.request.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.request.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.request.mapper.RequestMapper;
import ru.practicum.explore_with_me.request.model.Request;
import ru.practicum.explore_with_me.request.repository.RequestRepository;
import ru.practicum.explore_with_me.user.model.User;
import ru.practicum.explore_with_me.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository, RequestRepository requestRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<EventShortDto> getAllEvents(
            String text, String categories, Boolean paid, String rangeStart,
            String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size) {
        List<Event> events = eventRepository.findAllByAnnotationLikeAndCategoryIdAndPaid(
                text, Long.parseLong(categories), paid);
        return EventMapper.toEventShortDtoList(events);
    }

    @Override
    public EventFullDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + id + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + id + " нет"));
        return EventMapper.toEventFullDto(event, category);
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
            event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            event.setPaid(updateEventRequest.getPaid());
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
            event.setTitle(updateEventRequest.getTitle());

            eventRepository.save(event);
            return EventMapper.toEventFullDto(event, category);
        } else {
            throw new RuntimeException("Пользователь с id " + userId + " не может вносить изменения в это событие.");
        }
    }

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new RuntimeException("Такой категории c id " + newEventDto.getCategory() + " нет"));
        addLocation(newEventDto);
        Event event = EventMapper.toNewEvent(newEventDto, category, user);

        event.setState(Status.PENDING);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event, category);
    }

    @Override
    public Location addLocation(NewEventDto newEventDto) {
        Location location = newEventDto.getLocation();
        locationRepository.save(location);
        return location;
    }

    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toEventFullDto(event, category);
    }

    @Override
    public EventFullDto cancelEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        //todo: Обратите внимание:
        // Отменить можно только событие в состоянии ожидания модерации.
        if (event.getState().equals(Status.PENDING)) {
            event.setState(Status.CANCELED);
            return EventMapper.toEventFullDto(event, category);
        } else {
            throw new NotFoundException("Событие с id " + eventId + " не может быть отменено, т.к. прошло модерацию.");
        }
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return RequestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto confirmedRequestByUser(Long userId, Long eventId, Long reqId) {
        //todo: Обратите внимание:
        // если для события лимит заявок равен 0 или отключена пре-модерация заявок,
        // то подтверждение заявок не требуется
        // нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
        // если при подтверждении данной заявки, лимит заявок для события исчерпан,
        // то все неподтверждённые заявки необходимо отклонить
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(Status.CONFIRMED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public ParticipationRequestDto rejectedRequestByUser(Long userId, Long eventId, Long reqId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + reqId + " нет"));
        request.setStatus(Status.REJECTED);
        return RequestMapper.toParticipationRequestDto(request);
    }

    @Override
    public List<EventFullDto> findAllEventsByParams(
            List<Long> users, List<String> states, List<Long> categories,
            String rangeStart, String rangeEnd, Long from, Long size) {
        List<Status> statusList = getStatusFromString(states);
        LocalDateTime start = LocalDateTime.parse(rangeStart,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(rangeEnd,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Event> events = eventRepository
                .findAllByInitiator_IdInAndStateInAndCategoryIdInAndEventDateAfterAndEventDateBefore(
                        users, statusList, categories, start, end);
        return EventMapper.toEventFullDtoList(events);
    }

    private List<Status> getStatusFromString(List<String> state) {
        List<Status> statusList = new ArrayList<>();
        for (String entry : state) {
            statusList.add(Status.valueOf(entry));
        }
        return statusList;
    }

    @Override
    public EventFullDto updateEventById(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setAnnotation(adminUpdateEventRequest.getAnnotation());

        Long categoryId = adminUpdateEventRequest.getCategory();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + categoryId + " нет"));
        event.setCategory(event.getCategory());

        event.setDescription(adminUpdateEventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(adminUpdateEventRequest.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setLocation(adminUpdateEventRequest.getLocation());
        event.setPaid(adminUpdateEventRequest.getPaid());
        event.setParticipantLimit(adminUpdateEventRequest.getParticipantLimit());
        event.setRequestModeration(adminUpdateEventRequest.getRequestModeration());
        event.setTitle(adminUpdateEventRequest.getTitle());

        eventRepository.save(event);
        return EventMapper.toEventFullDto(event, category);
    }

    @Override
    public EventFullDto publishedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setState(Status.PUBLISHED);
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toEventFullDto(event, category);
    }

    @Override
    public EventFullDto rejectedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Такого события c id " + eventId + " нет"));
        event.setState(Status.CANCELED);
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper.toEventFullDto(event, category);
    }

}
