package ru.practicum.ewm.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.common.service.AddAndGetViewsImpl;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.model.Status;
import ru.practicum.ewm.event.model.ViewsAndCountConfirmed;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.LocationRepository;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.dto.UpdateEventRequest;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final AddAndGetViewsImpl addAndGetViewsImpl;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository, RequestRepository requestRepository, LocationRepository locationRepository, AddAndGetViewsImpl addAndGetViewsImpl) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
        this.addAndGetViewsImpl = addAndGetViewsImpl;
    }

    @Override
    public List<EventShortDto> getAllEvents(
            String text, List<Long> categories, Boolean paid, String rangeStart,
            String rangeEnd, Boolean onlyAvailable, String sort, Long from, Long size,
            HttpServletRequest request) {
        List<Event> events;
        Map<Event, Long> eventsWithCountConfirmed;
        if (sort != null && sort.equals("EVENT_DATE")) {
            events = eventRepository
                    .findAllByAnnotationLikeOrCategoryIdInAndPaidOrderByEventDateAsc(
                            text, categories, paid);
        } else if (sort != null && sort.equals("VIEWS")) {
            events = eventRepository
                    .findAllEventsByFilters(
                            text, categories, paid);
            eventsWithCountConfirmed = getEventsWithCountConfirmed(events);
            List<EventShortDto> viewsGroupEventShortDtoList = EventMapper
                    .toEventShortDtoList(getViewsAndCountConfirmedOfEvents(
                            eventsWithCountConfirmed));
            viewsGroupEventShortDtoList.sort(Comparator.comparing(EventShortDto::getViews));
            return viewsGroupEventShortDtoList;
        } else {
            events = eventRepository
                    .findAllEventsByFilters(
                            text, categories, paid);
        }
        try {
            addAndGetViewsImpl.addHit(request.getRequestURI(), request.getRemoteAddr());
        } catch (Exception exception) {
            log.error(exception.getCause().getMessage());
        }
        eventsWithCountConfirmed = getEventsWithCountConfirmed(events);
        return EventMapper.toEventShortDtoList(
                getViewsAndCountConfirmedOfEvents(eventsWithCountConfirmed));
    }

    private Map<Event, Long> getEventsWithCountConfirmed(List<Event> events) {
        Map<Event, Long> eventsWithCountConfirmed = new HashMap<>();
        List<Long> confirmedList = requestRepository
                .countByEventInAndStatusOrderByEvent(events, Status.CONFIRMED);
        for (int i = 0; i < confirmedList.size(); i++) {
            eventsWithCountConfirmed.put(events.get(i), confirmedList.get(i));
        }
        return eventsWithCountConfirmed;
    }

    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + id + " нет"));
        Long views = 0L;
        Long confirmedRequests = requestRepository
                .countEventByStatus(id, Status.CONFIRMED);
        try {
            addAndGetViewsImpl.addHit(
                    request.getRequestURI(), request.getRemoteAddr());
            views = addAndGetViewsImpl.getViewByEventId(
                    request.getRequestURI());
        } catch (Exception exception) {
            log.error(exception.getCause().getMessage());
        }
        EventFullDto eventFullDto;
        if (event.getState().equals(Status.PUBLISHED)) {
            eventFullDto = EventMapper.toPublishedEventFullDto(
                    event, event.getCategory(), views, confirmedRequests);
        } else {
            eventFullDto = EventMapper
                    .toEventFullDto(event, event.getCategory(), views, confirmedRequests);
        }
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getAllEventsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Такого пользователя c id " + userId + " нет"));
        List<Event> eventsByUser = eventRepository.findAllByInitiator(user);
        Map<Event, Long> eventsWithCountConfirmed = getEventsWithCountConfirmed(eventsByUser);
        return EventMapper.toEventShortDtoList(
                getViewsAndCountConfirmedOfEvents(eventsWithCountConfirmed));
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
            EventFullDto eventFullDto;
            if (event.getState().equals(Status.PUBLISHED)) {
                eventFullDto = EventMapper
                        .toPublishedEventFullDto(event, category, null, null);
            } else {
                eventFullDto = EventMapper
                        .toEventFullDto(event, category, null, null);
            }
            return eventFullDto;
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
        Event event = EventMapper.toNewEvent(newEventDto, category, user, Status.PENDING);
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event, category, null, null);
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
        return EventMapper.toEventFullDto(event, category, null, null);
    }

    @Override
    public EventFullDto cancelEventByUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        if (event.getState().equals(Status.PENDING)) {
            event.setState(Status.CANCELED);
            return EventMapper
                    .toEventFullDto(event, category, null, null);
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя c id " + userId + " нет"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        Request request = requestRepository.findById(reqId)
                .orElseThrow(() -> new NotFoundException("Такой заявки c id " + reqId + " нет"));
        if (request.getStatus().equals(Status.CONFIRMED) ||
                request.getStatus().equals(Status.CANCELED)) {
            throw new BadRequestException("Заявка уже имеет статус: " + request.getStatus());
        } else {
            request.setStatus(Status.CONFIRMED);
            requestRepository.save(request);
            return RequestMapper.toParticipationRequestDto(request);
        }
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
        requestRepository.save(request);
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
                .findEventsWithoutSomeQueries(
                        users, statusList, categories, start, end);
        Map<Event, Long> eventsWithCountConfirmed
                = getEventsWithCountConfirmed(events);
        return EventMapper.toEventFullDtoList(
                getViewsAndCountConfirmedOfEvents(eventsWithCountConfirmed));
    }

    private List<Status> getStatusFromString(List<String> state) {
        return state.stream()
                .map(Status::valueOf)
                .collect(Collectors.toList());
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
        EventFullDto eventFullDto;
        if (event.getState().equals(Status.PUBLISHED)) {
            eventFullDto = EventMapper
                    .toPublishedEventFullDto(event, category, null, null);
        } else {
            eventFullDto = EventMapper
                    .toEventFullDto(event, category, null, null);
        }
        return eventFullDto;
    }

    @Override
    public EventFullDto publishedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setState(Status.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper
                .toPublishedEventFullDto(event, category, null, null);
    }

    @Override
    public EventFullDto rejectedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события c id " + eventId + " нет"));
        event.setState(Status.CANCELED);
        eventRepository.save(event);
        Category category = categoryRepository.findById(event.getCategory().getId())
                .orElseThrow(() -> new NotFoundException("Такой категории c id " + event.getCategory() + " нет"));
        return EventMapper
                .toEventFullDto(event, category, null, null);
    }

    private Map<Event, ViewsAndCountConfirmed> getViewsAndCountConfirmedOfEvents(
            Map<Event, Long> eventsWithCountConfirmed) {
        List<String> toStatisticService = new ArrayList<>();
        for (Map.Entry<Event, Long> entry : eventsWithCountConfirmed.entrySet()) {
            toStatisticService.add("/events/" + entry.getKey().getId());
        }
        Map<Long, Long> viewsOfEventsId = addAndGetViewsImpl
                .getViewsOfEventsId(toStatisticService.toString());
        Map<Event, ViewsAndCountConfirmed> viewsAndCountConfirmedOfEvents = new HashMap<>();
        for (Map.Entry<Event, Long> entryFromConfirmed : eventsWithCountConfirmed.entrySet()) {
            Long views = viewsOfEventsId.get(entryFromConfirmed.getKey().getId());
            viewsAndCountConfirmedOfEvents.put(
                    entryFromConfirmed.getKey(),
                    new ViewsAndCountConfirmed(
                            views,
                            entryFromConfirmed.getValue()
                    )
            );
        }
        return viewsAndCountConfirmedOfEvents;
    }
}
